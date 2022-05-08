package com.konghuan.skipads.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.konghuan.skipads.bean.APP;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppConfig {

    private static Map<String, APP> appMap = null;

    public static Map<String, APP> getAppMap(Context context) {
        if (appMap != null){
            return appMap;
        }
        appMap = new HashMap<String, APP>(); //用来存储获取的应用信息数据　　　　　
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            APP app = new APP();
            app.setImageResourceId(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
            app.setName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            app.setEdition(packageInfo.versionName);
            app.setPackageName(packageInfo.packageName);
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)     {
                appMap.put(packageInfo.packageName, app);
            }
        }
        return appMap;
    }

    public static APP getAppByPackageName(Context context, String packageName){
        return getAppMap(context).get(packageName);
    }

}
