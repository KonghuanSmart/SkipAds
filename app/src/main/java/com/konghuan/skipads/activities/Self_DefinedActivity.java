package com.konghuan.skipads.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.konghuan.skipads.R;
import com.konghuan.skipads.adapter.MyAdapter;
import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.utils.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class Self_DefinedActivity extends AppCompatActivity {

    //listview
    private RecyclerView mRecyclerView;
    private List<APP> mAppList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_defined);

        getSupportActionBar().setTitle("自定义规则");

        initView();
        intData();
        initEvent();
    }

    public void tv1(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(Self_DefinedActivity.this).create();
        alertDialog.setTitle("使用说明");
        alertDialog.setMessage("会默认添加跳过规则，如果你有编程功底可以手动编辑规则，点击应用如何点击修改自定义跳过广告，就能手动编辑。");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void tv2(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(Self_DefinedActivity.this).create();
        alertDialog.setTitle("问题反馈");
        alertDialog.setMessage("有问题请加QQ群：123456789反馈");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    //RecyclerView操作
    private void initEvent() {
        myAdapter = new MyAdapter(this,"Self_Defined",mAppList);

        mRecyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void intData() {
        AppConfig appConfig = new AppConfig();
        mAppList = appConfig.getAppList(this);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }
}