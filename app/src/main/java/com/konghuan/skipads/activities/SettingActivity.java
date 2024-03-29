package com.konghuan.skipads.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Switch;

import androidx.annotation.RequiresApi;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.R;
import com.konghuan.skipads.service.SkipAdsService;

import java.util.Iterator;
import java.util.List;


public class SettingActivity extends Activity implements View.OnClickListener {

    private final String TAG = getClass().getName() + Constants.TAG_TAIL;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch hideMe;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch protectMe;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch showSkipToast;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //隐藏状态栏
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hideMe = findViewById(R.id.hide_me_switch);
        hideMe.setOnClickListener(this);
        protectMe = findViewById(R.id.protect_me_switch);
        protectMe.setOnClickListener(this);
        showSkipToast = findViewById(R.id.show_skip_toast);
        showSkipToast.setOnClickListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences();
        boolean hide_me = sharedPreferences.getBoolean("hide_me", true);
        hideMe.setChecked(hide_me);
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTasks = manager.getAppTasks();
        Iterator<ActivityManager.AppTask> iterator = appTasks.iterator();
        if (iterator.hasNext()){
            ActivityManager.AppTask appTask = iterator.next();
            appTask.setExcludeFromRecents(hide_me);
        }
        showSkipToast.setChecked(sharedPreferences.getBoolean("show_skip_toast", false));

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        if (hideMe != null && protectMe != null){
            if (isIgnoringBatteryOptimizations()){
                protectMe.setChecked(true);
            }
            SharedPreferences sharedPreferences = getSharedPreferences();
            boolean hide_me = sharedPreferences.getBoolean("hide_me", true);
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.AppTask> appTasks = manager.getAppTasks();
            Iterator<ActivityManager.AppTask> iterator = appTasks.iterator();
            if (iterator.hasNext()){
                ActivityManager.AppTask appTask = iterator.next();
                appTask.setExcludeFromRecents(hide_me);
            }
            hideMe.setChecked(hide_me);
        }
    }

    private SharedPreferences getSharedPreferences(){
        return getSharedPreferences(Constants.SHARE_NAME,MODE_PRIVATE);
    }

    private SharedPreferences.Editor getSharedPreferencesEditor(){
        return getSharedPreferences().edit();
    }

    private void saveShowSkipToast(){
        SharedPreferences.Editor editor = getSharedPreferencesEditor();
        editor.putBoolean("show_skip_toast",showSkipToast.isChecked());
        editor.apply();
        if (SkipAdsService.isRunningOn()){
            SkipAdsService.setShowTips(showSkipToast.isChecked());
        }
    }

    private void saveHideMe(boolean flag){
        SharedPreferences.Editor editor = getSharedPreferencesEditor();
        editor.putBoolean("hide_me", flag);
        editor.apply();
    }

    private void hideApplicationFromTaskList(){
        saveHideMe(hideMe.isChecked());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M){
            return true;
        }
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try {
            @SuppressLint("BatteryLife")
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            Log.d(TAG, "requestIgnoreBatteryOptimizations: "+e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void protectMeFromPowerManager(){
        androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(SettingActivity.this).create();
        alertDialog.setIcon(R.drawable.tips);
        alertDialog.setTitle("温馨提示");
        alertDialog.setMessage("需要您在 [应用信息] -> [省电策略] 手动设置本应用的后台配置！");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestIgnoreBatteryOptimizations();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG, "onClick: 取消");
            }
        });
        alertDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hide_me_switch:
                hideApplicationFromTaskList();
                onResume();
                break;
            case R.id.protect_me_switch:
                protectMeFromPowerManager();
                break;
            case R.id.show_skip_toast:
                saveShowSkipToast();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}