package com.konghuan.skipads.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.R;
import com.konghuan.skipads.entity.Rule;
import com.konghuan.skipads.service.RuleService;
import com.konghuan.skipads.service.SkipAdsService;
import com.konghuan.skipads.utils.ConfigUtil;

public class TimeActivity extends AppCompatActivity {

    private Handler handler;
    private TextView adContent;
    private TextView skipTimes;
    private static RuleService ruleService;
    private final int CHANGE_TEXT = 1;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_times, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_item:
                saveRule();
                break;
            case R.id.remove_item:
                removeRule();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void removeRule(){
        SharedPreferences.Editor editor = ConfigUtil.getSharedPreferencesEditor(TimeActivity.this, Constants.RECORD_INFO);
        editor.remove("packageName");
        editor.remove("id");
        editor.remove("isSave");
        editor.remove("value");
        editor.apply();
        onResume();
        Toast.makeText(this, "已清除该记录！", Toast.LENGTH_LONG).show();
    }

    public void saveRule(){
        SharedPreferences sharedPreferences = ConfigUtil.getSharedPreferences(TimeActivity.this, Constants.RECORD_INFO);
        String packageName = sharedPreferences.getString("packageName", null);
        String rule = sharedPreferences.getString("id",null);
        if (packageName != null && rule != null){
            Rule rule1 = ruleService.getRuleByName(packageName);
            if (rule1 != null){
                if (rule1.getRule().equals(rule)){
                    Toast.makeText(this, "Ta已经被保存辣！！！", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    ruleService.updateRule(packageName, rule);
                }
            }else {
                ruleService.addRule(packageName,rule);
            }
            if (SkipAdsService.isRunningOn()){
                SkipAdsService.updateRules(packageName, rule);
            }
            Toast.makeText(this, "保存成功！", Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(this, "保存失败！", Toast.LENGTH_LONG).show();
        }

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ruleService = new RuleService(this);
        setContentView(R.layout.activity_time);

        getSupportActionBar().setTitle("跳过详情");

        adContent = findViewById(R.id.ad_content);
        skipTimes = findViewById(R.id.skip_times);
        TextView btn1 = findViewById(R.id.btn1);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog对话框
                AlertDialog alertDialog = new AlertDialog.Builder(TimeActivity.this).create();
                alertDialog.setTitle("使用说明");
                alertDialog.setMessage("每自动关闭一个广告就会添加次数");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });

        TextView btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AlertDialog对话框
                AlertDialog alertDialog = new AlertDialog.Builder(TimeActivity.this).create();
                alertDialog.setTitle("问题反馈");
                alertDialog.setMessage("有问题请加QQ群：123456789反馈");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alertDialog.show();
            }
        });

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == CHANGE_TEXT){
                    SharedPreferences sharedPreferences = ConfigUtil.getSharedPreferences(TimeActivity.this, Constants.RECORD_INFO);
                    String value = sharedPreferences.getString("value", "什么也没有哇！");
                    adContent.setText(value);
                    int times = sharedPreferences.getInt("times", 0);
                    skipTimes.setText("累计跳过广告次数："+times);
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message msg = new Message();
        msg.what = CHANGE_TEXT;
        handler.sendMessage(msg);
    }
}