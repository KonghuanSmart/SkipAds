package com.konghuan.skipads.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.konghuan.skipads.R;
import com.konghuan.skipads.utils.AppConfig;

/**
 * 开屏页面
 *
 */
public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle bundle) {
        final View view = View.inflate(this, R.layout.activity_splash, null);

        //隐藏状态栏
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(view);
        super.onCreate(bundle);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(() -> {
            AppConfig.getAppList(SplashActivity.this);
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }).start();
    }
}