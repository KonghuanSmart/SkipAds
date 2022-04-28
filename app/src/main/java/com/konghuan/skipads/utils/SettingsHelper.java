package com.konghuan.skipads.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.service.SkipAdsService;

import java.util.ArrayList;
import java.util.List;

public class SettingsHelper {
    private static final String TAG = SettingsHelper.class.getName() + Constants.TAG_TAIL;
    private static Context mContext;

    public static List<String> getThirdAppList(Context context)
    {
        PackageManager packageManager = context.getPackageManager();
        @SuppressLint("QueryPermissionsNeeded")
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        List<String> thirdAPP = new ArrayList<>();
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo pak = packageInfoList.get(i);
            //判断是否为系统预装的应用
            if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
                thirdAPP.add( pak.packageName);
            }
        }
        return thirdAPP ;

    }

    public static String[] getPackageNames(){
        List<String> thirdAppList = getThirdAppList(mContext);
        return thirdAppList.toArray(new String[thirdAppList.size()]);
    }

    public static boolean isAccessibilityServiceSettingsOn(Context context){
        if (mContext == null)
            mContext = context;
        int accessibilityServiceEnabled = 0;
        final String service = mContext.getPackageName() + "/" + SkipAdsService.class.getCanonicalName();
        try {
            accessibilityServiceEnabled = Settings.Secure.getInt(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
            Log.v(TAG,"Enabled = " + accessibilityServiceEnabled);
        }catch (Exception e){
            Log.e(TAG,"accessibility to not found: "
                    + e.getMessage());
        }
        TextUtils.SimpleStringSplitter mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');

        if (accessibilityServiceEnabled == 1) {
            String settingValue = Settings.Secure.getString(
                    mContext.getApplicationContext().getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (settingValue != null) {
                mStringColonSplitter.setString(settingValue);
                while (mStringColonSplitter.hasNext()) {
                    String accessibilityService = mStringColonSplitter.next();
                    if (accessibilityService.equalsIgnoreCase(service)) {
                        return true;
                    }
                }
            }
        } else {
            Log.v(TAG, "未获取到无障碍权限");
        }

        return false;
    }

    public static void applyForAccessibilityPermission(){
        try {
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            mContext.startActivity(intent);
        }catch (Exception e){
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            mContext.startActivity(intent);
        }
    }

}
