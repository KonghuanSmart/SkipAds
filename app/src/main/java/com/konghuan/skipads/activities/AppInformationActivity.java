package com.konghuan.skipads.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.konghuan.skipads.R;
import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.service.AppService;
import com.konghuan.skipads.service.RuleService;
import com.konghuan.skipads.service.SkipAdsService;
import com.konghuan.skipads.utils.AppConfig;

public class AppInformationActivity extends AppCompatActivity {

    private ImageView iv_AppImg;
    private TextView tv_AppName;
    private TextView tv_AppEdition;
    private Button btn_add;
    private Button btn_renewal;
    private Button btn_delete;
    private Button btn_white_on;
    private Button btn_white_off;

    private String activityName;
    private String AppPackageName;
    private AppService service;
    private RuleService rService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);
        rService = new RuleService(this);
        initView();
        initDate();

        APP app = AppConfig.getAppByPackageName(this,AppPackageName);

        getSupportActionBar().setTitle(app.getName());

        tv_AppEdition.setText("版本：" + app.getEdition());
        tv_AppName.setText(app.getName());
        iv_AppImg.setImageDrawable(app.getImg());

        switch (activityName){
            case "Reminder":
            //默认没有跳过提示
            btn_add.setVisibility(View.INVISIBLE);
            btn_renewal.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
            btn_white_on.setVisibility(View.INVISIBLE);
            btn_white_off.setVisibility(View.INVISIBLE);
            break;
            case "White":
            //默认没有在白名单
            btn_add.setVisibility(View.INVISIBLE);
            btn_renewal.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
            btn_white_on.setVisibility(View.INVISIBLE);
            btn_white_off.setVisibility(View.VISIBLE);
            break;
            case "Self_Defined":if (Rule_exist(AppPackageName)){
                //自定义规则默认为没有
                btn_add.setVisibility(View.VISIBLE);
                btn_renewal.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
                btn_white_on.setVisibility(View.INVISIBLE);
                btn_white_off.setVisibility(View.INVISIBLE);
            }else{
                btn_add.setVisibility(View.INVISIBLE);
                btn_renewal.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                btn_white_on.setVisibility(View.INVISIBLE);
                btn_white_off.setVisibility(View.INVISIBLE);
            }break;
            case "LocalAppList":
            //自定义规则默认为没有
            btn_add.setVisibility(View.INVISIBLE);
            btn_renewal.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
            btn_white_on.setVisibility(View.VISIBLE);
            btn_white_off.setVisibility(View.INVISIBLE);
            break;
        }

        getSupportActionBar().setTitle(app.getName());

    }

    public void initView(){
        iv_AppImg = findViewById(R.id.iv_AppName);
        tv_AppName = findViewById(R.id.tv_AppName);
        tv_AppEdition = findViewById(R.id.tv_AppEdition);
        btn_add = findViewById(R.id.btn_add);
        btn_renewal = findViewById(R.id.btn_renewal);
        btn_delete = findViewById(R.id.btn_delete);
        btn_white_on = findViewById(R.id.btn_white_on);
        btn_white_off = findViewById(R.id.btn_white_off);
    }

    public void initDate(){
        Intent intent = getIntent();
        AppPackageName = intent.getStringExtra("AppPackage");
        activityName = intent.getStringExtra("ActivityName");
    }

    public void buttonadd(View view) {
        EditText editText = new EditText(this);
        editText.requestFocus();
        editText.setFocusable(true);
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(AppInformationActivity.this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("请填写你的规则：");
        alertDialog.setView(editText);

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", (dialog, which) -> {

        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", (dialog, which) -> {
            String text = String.valueOf(editText.getText());
            if (!TextUtils.isEmpty(text)){
                int i = rService.addRule(AppPackageName, text);
                if (i > 0){
                    Toast.makeText(AppInformationActivity.this, "添加成功!", Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(AppInformationActivity.this, "添加失败!", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(AppInformationActivity.this, "规则不能为空!", Toast.LENGTH_LONG).show();
            }

        });
        alertDialog.show();
    }

    public void buttonrenewal(View view) {
        self_Set();
    }

    public void buttondelete(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(AppInformationActivity.this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("确定要删除吗？");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            rService = new RuleService(AppInformationActivity.this);
            int i = rService.delRule(AppPackageName);
            if (i > 0){
                Toast.makeText(AppInformationActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
                if (SkipAdsService.isRunningOn()){
                    SkipAdsService.updateRules(AppPackageName);
                }
                finish();
            }else {
                Toast.makeText(AppInformationActivity.this, "删除失败!", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }

    public void button_white_on(View view) {
        if (White_Reminder_exist(AppPackageName, "White")){
            service = new AppService(this);
            service.addApp(AppPackageName,"White");
            if (SkipAdsService.isRunningOn()){
                SkipAdsService.addToWhiteList(AppPackageName);
            }
            finish();
            Toast.makeText(AppInformationActivity.this, "成功添加到白名单!", Toast.LENGTH_LONG).show();
        }else {
            remind();
        }
    }

    public void button_white_off(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(AppInformationActivity.this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("确定要移出白名单吗？");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                service = new AppService(AppInformationActivity.this);
                service.delApp(AppPackageName,"White");
                if (SkipAdsService.isRunningOn()){
                    SkipAdsService.removeFromWhiteList(AppPackageName);
                }
                finish();
                Toast.makeText(AppInformationActivity.this, "成功从白名单中移出!", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }

    public boolean Rule_exist(String appPackageName){
        rService = new RuleService(this);
        if (rService.getRuleByName(appPackageName) == null){
            return true;
        }else {
            return false;
        }
    }

    public boolean White_Reminder_exist(String appPackageName, String activityName2){
        service = new AppService(this);
        if (service.getAppByName(appPackageName, activityName2) == null){
            return true;
        }else {
            return false;
        }
    }

    private void remind() {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(AppInformationActivity.this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("不能重复添加相同的应用");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {});
        alertDialog.show();
    }

    private void self_Set(){
        EditText editText = new EditText(this);
        editText.setText(rService.getRuleByName(AppPackageName).getRule());
        editText.requestFocus();
        editText.setFocusable(true);
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(AppInformationActivity.this).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage("请修改你的规则：");
        alertDialog.setView(editText);

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", (dialog, which) -> {

        });
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定", (dialog, which) -> {
            int i = rService.updateRule(AppPackageName, String.valueOf(editText.getText()));
            if (i > 0){
                if (SkipAdsService.isRunningOn()){
                    SkipAdsService.updateRules(AppPackageName, String.valueOf(editText.getText()));
                }
                Toast.makeText(AppInformationActivity.this, "修改成功!", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(AppInformationActivity.this, "修改失败!", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }
}