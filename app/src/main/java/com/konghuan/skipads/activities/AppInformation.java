package com.konghuan.skipads.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.konghuan.skipads.R;
import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.service.AppService;
import com.konghuan.skipads.utils.AppConfig;

public class AppInformation extends AppCompatActivity {

    private ImageView iv_AppImg;
    private TextView tv_AppName;
    private TextView tv_AppEdition;
    private Button btn_add;
    private Button btn_renewal;
    private Button btn_delete;
    private Button btn_white_on;
    private Button btn_white_off;
    private Button btn_reminder_on;
    private Button btn_reminder_off;

    private String activityName;
    private String AppPackageName;
    private AppService service;

    private boolean temp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);

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
            btn_reminder_on.setVisibility(View.VISIBLE);
            btn_reminder_off.setVisibility(View.INVISIBLE);
            break;
            case "White":
            //默认没有在白名单
            btn_add.setVisibility(View.INVISIBLE);
            btn_renewal.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
            btn_white_on.setVisibility(View.INVISIBLE);
            btn_white_off.setVisibility(View.VISIBLE);
            btn_reminder_on.setVisibility(View.INVISIBLE);
            btn_reminder_off.setVisibility(View.INVISIBLE);
            break;
            case "Self_Defined":if (temp){
                //自定义规则默认为没有
                btn_add.setVisibility(View.VISIBLE);
                btn_renewal.setVisibility(View.INVISIBLE);
                btn_delete.setVisibility(View.INVISIBLE);
                btn_white_on.setVisibility(View.INVISIBLE);
                btn_white_off.setVisibility(View.INVISIBLE);
                btn_reminder_on.setVisibility(View.INVISIBLE);
                btn_reminder_off.setVisibility(View.INVISIBLE);
            }else{
                btn_add.setVisibility(View.INVISIBLE);
                btn_renewal.setVisibility(View.VISIBLE);
                btn_delete.setVisibility(View.VISIBLE);
                btn_white_on.setVisibility(View.INVISIBLE);
                btn_white_off.setVisibility(View.INVISIBLE);
                btn_reminder_on.setVisibility(View.INVISIBLE);
                btn_reminder_off.setVisibility(View.INVISIBLE);
            }break;
            case "LocalAppList":
            //自定义规则默认为没有
            btn_add.setVisibility(View.INVISIBLE);
            btn_renewal.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
            btn_white_on.setVisibility(View.VISIBLE);
            btn_white_off.setVisibility(View.INVISIBLE);
            btn_reminder_on.setVisibility(View.INVISIBLE);
            btn_reminder_off.setVisibility(View.VISIBLE);
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
        btn_reminder_on = findViewById(R.id.btn_reminder_on);
        btn_reminder_off = findViewById(R.id.btn_reminder_off);
    }

    public void initDate(){
        Intent intent = getIntent();
        AppPackageName = intent.getStringExtra("AppPackage");
        activityName = intent.getStringExtra("ActivityName");
    }

    public void buttonadd(View view) {
    }

    public void buttonrenewal(View view) {
    }

    public void buttondelete(View view) {
    }

    public void button_white_on(View view) {
        service = new AppService(this);
        service.addApp(AppPackageName,"White");
    }

    public void button_white_off(View view) {
        service = new AppService(this);
        service.delApp(AppPackageName,"White");
    }

    public void button_reminder_on(View view) {
        service = new AppService(this);
        service.delApp(AppPackageName,"Reminder");
    }

    public void button_reminder_off(View view) {
        service = new AppService(this);
        service.addApp(AppPackageName,"Reminder");
    }
}