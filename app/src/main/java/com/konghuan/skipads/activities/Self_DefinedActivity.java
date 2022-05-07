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

import java.util.ArrayList;
import java.util.List;

public class Self_DefinedActivity extends AppCompatActivity {

    //listview
    private RecyclerView mRecyclerView;
    private List<APP> mAppList;
    private MyAdapter myAdapter;

    private MySQLiteOpenHelper mMySQLiteOpenHelper;

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
        alertDialog.setMessage("点击设置添加自定义跳过规则");
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
        myAdapter = new MyAdapter(this,mAppList);

        mRecyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void intData() {
        mAppList = new ArrayList<>();

        List<APP> appList = new ArrayList<APP>(); //用来存储获取的应用信息数据　　　　　
        List<PackageInfo> packages = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            APP tmpInfo = new APP();
            tmpInfo.setImageResourceId(packageInfo.applicationInfo.loadIcon(getPackageManager()));
            tmpInfo.setName(packageInfo.applicationInfo.loadLabel(getPackageManager()).toString());
            tmpInfo.setEdition(packageInfo.versionName);
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)     {
                appList.add(tmpInfo);//如果非系统应用，则添加至appList
            }
        }
        mAppList = appList;
    }

    private List<APP> getDataFromDB() {
        return mMySQLiteOpenHelper.queryAllFromDb();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }

    //数据库操作
    //添加数据
    public void insert() {
        int img = 1;//转换数据，应该这样写 etImg.getText().toString().trim();
        String name = "1";//转换数据，应该这样写 etName.getText().toString().trim();

        APP app = new APP();
        app.setName(name);
//        app.setImageResourceId(img);

        //插入数据库中
        mMySQLiteOpenHelper.insertData(app);
    }

    //删除数据
    public void delect() {
        String name = "1";

        //按姓名删除
        mMySQLiteOpenHelper.delectByName(name);

    }
}