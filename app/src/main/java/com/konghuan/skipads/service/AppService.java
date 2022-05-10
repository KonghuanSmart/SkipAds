package com.konghuan.skipads.service;

import android.content.Context;

import com.konghuan.skipads.Dao.AppDao;
import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.service.Impl.AppDaoImpl;

import java.util.List;
import java.util.Map;

public class AppService {

    private static AppDao appDao;

    public AppService(Context context) {
        appDao = new AppDaoImpl(context);
    }

    public int addApp(String packagename, String activityname){
        return appDao.addApp(packagename, activityname);
    }

    public int delApp(String packagename, String activityname){
        return appDao.delApp(packagename, activityname);
    }

    public String getAppByName(String packagename){
        return appDao.getAppByName(packagename);
    }

//    public int updateApp(String packagename, String activityname){
//        return appDao.updateApp(packagename, activityname);
//    }

    public List<String> getAllApp(String avtivityName){
        return appDao.getAllApp(avtivityName);
    }
}
