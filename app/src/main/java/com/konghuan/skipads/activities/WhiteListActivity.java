package com.konghuan.skipads.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.konghuan.skipads.R;
import com.konghuan.skipads.adapter.MyAdapter;
import com.konghuan.skipads.bean.APP;

import java.util.ArrayList;
import java.util.List;

public class WhiteListActivity extends AppCompatActivity {

    //listview
    private RecyclerView mRecyclerView;
    private List<APP> mAppList;
    private MyAdapter myAdapter;

    private MySQLiteOpenHelper mMySQLiteOpenHelper;

    //获取数据库保存的图片
    private int[] imgs = {
            R.drawable.ic_launcher_background
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whitelist);

        getSupportActionBar().setTitle("自定义规则");

        initView();
        intData();
        initEvent();

        Button btn = findViewById(R.id.btn1);
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(WhiteListActivity.this,"长按了管理",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    public void bt(View view) {
        Toast.makeText(WhiteListActivity.this,"点击了设置",Toast.LENGTH_SHORT).show();
    }

    //不会写长按
//    public void onLongClinck(View v){
//        Toast.makeText(Self_Defined.this,"长按了设置",Toast.LENGTH_SHORT).show();
//    }

    public void tv1(View view) {
        //AlertDialog对话框
        AlertDialog alertDialog = new AlertDialog.Builder(WhiteListActivity.this).create();
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
        AlertDialog alertDialog = new AlertDialog.Builder(WhiteListActivity.this).create();
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

        APP newsBean1 = new APP();
        newsBean1.setName("雨中漫步");
        newsBean1.setImageResourceId(imgs[0]);

        APP newsBean2 = new APP();
        newsBean2.setName("林间穿梭");
        newsBean2.setImageResourceId(imgs[0]);

        APP newsBean3 = new APP();
        newsBean3.setName("旅行花海");
        newsBean3.setImageResourceId(imgs[0]);

        APP newsBean4 = new APP();
        newsBean4.setName("非平衡的线");
        newsBean4.setImageResourceId(imgs[0]);

        APP newsBean5 = new APP();
        newsBean5.setName("随心所欲");
        newsBean5.setImageResourceId(imgs[0]);

        APP newsBean6 = new APP();
        newsBean6.setName("大王范儿");
        newsBean6.setImageResourceId(imgs[0]);

        APP newsBean7 = new APP();
        newsBean7.setName("宇航局");
        newsBean7.setImageResourceId(imgs[0]);

        mAppList.add(newsBean1);
        mAppList.add(newsBean2);
        mAppList.add(newsBean3);
        mAppList.add(newsBean4);
        mAppList.add(newsBean5);
        mAppList.add(newsBean6);
        mAppList.add(newsBean7);
        mAppList.add(newsBean1);
        mAppList.add(newsBean2);
        mAppList.add(newsBean3);
        mAppList.add(newsBean4);
        mAppList.add(newsBean5);
        mAppList.add(newsBean6);
        mAppList.add(newsBean7);

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
        app.setImageResourceId(img);

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