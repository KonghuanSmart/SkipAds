package com.konghuan.skipads.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.R;
import com.konghuan.skipads.entity.Rule;
import com.konghuan.skipads.service.RuleService;
import com.konghuan.skipads.service.SkipAdsService;
import com.konghuan.skipads.utils.SettingsHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private final String TAG = getClass().getName() + Constants.TAG_TAIL;

    private Button btn;
    private RuleService service;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("SkipAds");

        service = new RuleService(MainActivity.this);
        btn = findViewById(R.id.tv1);
        btn.setOnClickListener(this);

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn1.setOnTouchListener(this);

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn2.setOnTouchListener(this);

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn4.setOnTouchListener(this);

        TextView btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(this);

        TextView btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(this);

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn7.setOnTouchListener(this);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void logout(){
        SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor edit = spf.edit();
        edit.putBoolean("isLogin",false);
        edit.apply();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void doLogout(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("⚠退出登录");
        alertDialog.setMessage("确定要退出登录吗？");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            logout();
        });
        alertDialog.show();
    }

    private void exit(){
        System.exit(0);
    }

    private void doExit(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("⚠退出软件");
        alertDialog.setMessage("您确定要退出软件吗？退出后您将无法享受到软件为自动跳过开屏广告！");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            exit();
        });
        alertDialog.show();
    }

    private void exportRules(){
        Map<String, String> allRule = service.getAllRule();
        int size = allRule.size();
        AlertDialog result = new AlertDialog.Builder(MainActivity.this).create();
        result.setTitle("提示");
        result.setMessage(size > 0? "共发现["+ size +"]条规则，是否导出?":"当前数据库中无任何规则!");
        result.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            if (size > 0){
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, String> entry : allRule.entrySet()){
                    try {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    } catch (JSONException e) {
                    }
                }
                ClipboardManager clipboard = (ClipboardManager) MainActivity.this.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText(null, jsonObject.toString());
                clipboard.setPrimaryClip(clipData);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("提示");
                alertDialog.setMessage("已将所有规则导出至剪贴板!");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog1, which1) -> {});
                alertDialog.show();
            }
        });
        result.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", (dialog, which) -> {

        });
        result.show();
    }

    private void importRules(){
        AlertDialog result = new AlertDialog.Builder(MainActivity.this).create();
        result.setTitle("提示");
        result.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {});
        EditText editText = new EditText(this);
        editText.requestFocus();
        editText.setFocusable(true);
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("请填写你的规则{ \"package\" : \"rule\"}");
        alertDialog.setView(editText);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "从剪贴板导入", ((dialog, which) -> {
            List<Rule> ruleFromClipboard = service.getRuleFromClipboard(MainActivity.this);
            if (ruleFromClipboard != null && ruleFromClipboard.size() > 0){
                int i = service.importRules(ruleFromClipboard);
                result.setMessage("成功导入["+i+"]条规则!");
            }else {
                result.setMessage("导入规则失败，不合规范的规则!");
            }
            result.show();
        }));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"导入", (dialog, which) -> {
            Editable text = editText.getText();
            if (!TextUtils.isEmpty(text)){
                List<Rule> rules = service.analysisRules(String.valueOf(text));
                if (rules.size() > 0){
                    int i = service.importRules(rules);
                    result.setMessage(i > 0 ? "成功导入["+i+"]条规则!":"导入规则失败!");
                }else {
                    result.setMessage("导入规则失败，不合规范的规则!");
                }
            }else {
                result.setMessage("导入规则失败，规则不能为空!");
            }
            result.show();
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "取消", ((dialog, which) -> {

        }));

        alertDialog.show();
    }

    private void showInstructions(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("使用说明");
        alertDialog.setMessage("点击圆圈打开/关闭跳过广告APP");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
        });
        alertDialog.show();
    }

    private void showHaveQuestion(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("问题反馈");
        alertDialog.setMessage("有问题请加QQ群：123456789反馈");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
        });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SkipAdsService.isRunningOn()){
            btn.setBackgroundResource((R.drawable.service_on));
        }else {
            btn.setBackgroundResource((R.drawable.service_off));
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.export_rule_item:
                exportRules();
                break;
            case R.id.import_rule_item:
                importRules();
                break;
            case R.id.logout_item:
                doLogout();
                break;
            case R.id.exit_item:
                doExit();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTips(){
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setIcon(R.drawable.tips);
        alertDialog.setTitle("温馨提示");
        alertDialog.setMessage("    即将跳转到系统授权界面，需要您在 [已下载的应用] -> [SkipAds] 中打开本应用的服务！");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SettingsHelper.applyForAccessibilityPermission(MainActivity.this);
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: 取消");
            }
        });
        alertDialog.show();
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.tv1:
                if (!SkipAdsService.isRunningOn()){
                    showTips();
                }
                break;
            case R.id.btn1:
                intent = new Intent(MainActivity.this, WhiteListActivity.class);
                startActivity(intent);
                break;
            case R.id.btn2:
                intent = new Intent(MainActivity.this, Self_DefinedActivity.class);
                startActivity(intent);
                break;
            case R.id.btn4:
                intent = new Intent(MainActivity.this, CountActivity.class);
                startActivity(intent);
                break;
            case R.id.btn5:
                showInstructions();
                break;
            case R.id.btn6:
                showHaveQuestion();
                break;
            case R.id.btn7:
                intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            v.setBackgroundResource(R.drawable.button_style_on_touch);
        }else if (event.getAction() == MotionEvent.ACTION_UP){
            v.setBackgroundResource(R.drawable.button_style);
        }
        return false;
    }
}