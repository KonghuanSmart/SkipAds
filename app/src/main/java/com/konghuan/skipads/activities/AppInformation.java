package com.konghuan.skipads.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.konghuan.skipads.R;

public class AppInformation extends AppCompatActivity {

    private boolean temp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_information);

        //接收数据
        Intent intent = getIntent();
//        Drawable img = intent.getIntExtra("img",drawable);
        String AppName = intent.getStringExtra("AppName");
        String AppEdition = intent.getStringExtra("AppEdition");

        getSupportActionBar().setTitle(AppName);

//        ImageView iv_AppName = findViewById(R.id.iv_AppName);
        TextView tv_AppName = findViewById(R.id.tv_AppName);
        TextView tv_AppEdition = findViewById(R.id.tv_AppEdition);
        Button btn_add = findViewById(R.id.btn_add);
        Button btn_renewal = findViewById(R.id.btn_renewal);
        Button btn_delete = findViewById(R.id.btn_delete);

//        iv_AppName.setImageResource(R.drawable.show_skip_toast);
        tv_AppName.setText(AppName);
        tv_AppEdition.setText("版本：" + AppEdition);

        if (temp) {
            btn_add.setVisibility(View.INVISIBLE);
            btn_renewal.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
        } else {
            btn_add.setVisibility(View.VISIBLE);
            btn_renewal.setVisibility(View.INVISIBLE);
            btn_delete.setVisibility(View.INVISIBLE);
        }

        getSupportActionBar().setTitle(AppName);
    }

    public void buttonadd(View view) {
    }

    public void buttonrenewal(View view) {
    }

    public void buttondelete(View view) {
    }
}