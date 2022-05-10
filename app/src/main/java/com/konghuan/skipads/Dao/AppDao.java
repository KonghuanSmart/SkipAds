package com.konghuan.skipads.Dao;

import com.konghuan.skipads.bean.APP;
import com.konghuan.skipads.entity.Rule;

import java.util.List;
import java.util.Map;

public interface AppDao {

    int addApp(String packagename, String avtivityname);
//    int updateApp(String packagename, String avtivityname);
    String getAppByName(String packagename);
    int delApp(String packagename, String avtivityname);
    List<String> getAllApp(String avtivityName);
}