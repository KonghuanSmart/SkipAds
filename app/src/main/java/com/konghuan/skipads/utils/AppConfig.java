package com.konghuan.skipads.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.konghuan.skipads.bean.APP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppConfig {

    private static List<APP> appList = null;

    public static List<APP> getAppList(Context context) {
        if (appList != null){
            return appList;
        }
        appList = new ArrayList<>(); //用来存储获取的应用信息数据　　　　　
        List<PackageInfo> packages = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            APP app = new APP();
            app.setImageResourceId(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
            app.setName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            app.setEdition(packageInfo.versionName);
            app.setPackageName(packageInfo.packageName);
            if((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM)==0)     {
                appList.add(app);//如果非系统应用，则添加至appList
            }
        }
        return appList;
    }

    public static APP getAppByPackageName(Context context, String packageName){
        List<APP> list = getAppList(context);
        Iterator<APP> iterator = list.iterator();
        if (iterator.hasNext()){
            APP next = iterator.next();
            if (next.getPackageName().equals(packageName)) {
                return next;
            }
        }
        return null;
    }

}
