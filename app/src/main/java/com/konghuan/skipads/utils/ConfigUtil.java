package com.konghuan.skipads.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigUtil {

    public static SharedPreferences getSharedPreferences(Context context, String name){
        return context.getSharedPreferences(name, Context.MODE_MULTI_PROCESS);
    }

    public static SharedPreferences.Editor getSharedPreferencesEditor(Context context, String name){
        return getSharedPreferences(context, name).edit();
    }



}
