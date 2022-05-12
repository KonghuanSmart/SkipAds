package com.konghuan.skipads.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konghuan.skipads.R;
import com.konghuan.skipads.adapter.MyAdapter;
import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.utils.AppConfig;

import java.util.List;

public class LocalAppListActivity extends AppCompatActivity {

    //listview
    private RecyclerView mRecyclerView;
    private List<APP> mAppList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_app_list);

        getSupportActionBar().setTitle("添加本地APP");

        initView();
        intData();
        initEvent();
    }

    //RecyclerView操作
    private void initEvent() {
        myAdapter = new MyAdapter(this,"LocalAppList",mAppList);

        mRecyclerView.setAdapter(myAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
    }

    private void intData() {
        mAppList = AppConfig.getAppList(this);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.rlv);
    }
}