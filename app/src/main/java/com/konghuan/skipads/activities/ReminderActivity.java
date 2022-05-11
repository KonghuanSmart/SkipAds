package com.konghuan.skipads.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.konghuan.skipads.R;
import com.konghuan.skipads.adapter.MyAdapter;
import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.service.AppService;
import com.konghuan.skipads.utils.AppConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    //listview
    private RecyclerView mRecyclerView;
    private List<APP> mAppList;
    private MyAdapter myAdapter;
    private AppService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        getSupportActionBar().setTitle("跳过提示");

        initView();
        intData();
        initEvent();
    }

    public void tv1(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(ReminderActivity.this).create();
        alertDialog.setTitle("使用说明");
        alertDialog.setMessage("跳过提示默认隐藏，可以通过点击目录，再点击添加应用来选择想要显示跳过提示的APP。");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }

    public void tv2(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(ReminderActivity.this).create();
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
        myAdapter = new MyAdapter(this,"Reminder",mAppList);

        mRecyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void intData() {
        mAppList = new ArrayList<>();

        service = new AppService(this);
        List<String> allApp = service.getAllApp("Reminder");
        Iterator<String> iterator = allApp.iterator();
        String next;
        while (iterator.hasNext()){
            next = iterator.next();
            mAppList.add(AppConfig.getAppByPackageName(this, next));
        }
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_white_reminder, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout_localapp) {
            Intent intent = new Intent(ReminderActivity.this, LocalAppListActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}