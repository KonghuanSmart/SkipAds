package com.konghuan.skipads.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.konghuan.skipads.R;
import com.konghuan.skipads.utils.AppConfig;
import com.konghuan.skipads.utils.ConfigUtil;
import com.konghuan.skipads.utils.SettingsHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("SkipAds");

        Button btn = findViewById(R.id.tv1);
        btn.setOnClickListener(new View.OnClickListener() {
            int flag = 0;
            @Override
            public void onClick(View v) {

                if (!SettingsHelper.isAccessibilityServiceSettingsOn(MainActivity.this)){
                    SettingsHelper.applyForAccessibilityPermission();
                }
                switch (flag) {
                    case 0:
                        btn.setBackgroundResource((R.drawable.button_style3));//点击按钮修改颜色
                        flag = 1;
                        break;
                    case 1:
                        btn.setBackgroundResource((R.drawable.button_style2));
                        flag = 0;
                        break;
                }
            }
        });

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WhiteListActivity.class);
            startActivity(intent);
        });

        Button btn2 = findViewById(R.id.btn2);
        btn2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Self_DefinedActivity.class);
            startActivity(intent);
        });

        Button btn3 = findViewById(R.id.btn3);
        btn3.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(intent);
        });

        Button btn4 = findViewById(R.id.btn4);
        btn4.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TimeActivity.class);
            startActivity(intent);
        });

        Button btn7 = findViewById(R.id.btn7);
        btn7.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        TextView btn5 = findViewById(R.id.btn5);
        btn5.setOnClickListener(v -> {
            //AlertDialog对话框
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("使用说明");
            alertDialog.setMessage("点击圆圈打开/关闭跳过广告APP");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            });
            alertDialog.show();
        });

        TextView btn6 = findViewById(R.id.btn6);
        btn6.setOnClickListener(v -> {
            //AlertDialog对话框
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("问题反馈");
            alertDialog.setMessage("有问题请加QQ群：123456789反馈");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            });
            alertDialog.show();
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void doLogout(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("⚠退出登录");
        alertDialog.setMessage("确定要退出登录吗？");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            SharedPreferences spf = getSharedPreferences("spfRecord",MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor edit = spf.edit();
            edit.putBoolean("isLogin",false);
            edit.apply();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        });
        alertDialog.show();
    }

    private void doExit(){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("⚠退出软件");
        alertDialog.setMessage("您确定要退出软件吗？退出后您将无法享受到软件为自动跳过开屏广告！");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", (dialog, which) -> {
            System.exit(0);
        });
        alertDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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


}