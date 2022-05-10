package com.konghuan.skipads.service.Impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.Dao.AppDao;
import com.konghuan.skipads.db.SingletSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AppDaoImpl implements AppDao{

    private static SQLiteDatabase db;
    private final Context mContext;

    public AppDaoImpl(Context mContext) {
        this.mContext = mContext;
        if (db == null){
            synchronized (AppDaoImpl.class){
                if (db == null){
                    db = SingletSQLiteDatabase.getSingletSQLiteDatabase(mContext).getWritableDatabase();
                }
            }
        }
    }

    @Override
    public int addApp(String packagename, String activityname) {
        SQLiteStatement statement = null;
        switch (activityname){
            case "White" : statement = db.compileStatement("insert into "+ Constants.TABLE_WHITE+"(packagename) values(?)");
            break;

            case "Reminder" : statement = db.compileStatement("insert into "+ Constants.TABLE_REMINDER+"(packagename) values(?)");
            break;
        }
        statement.bindString(1, packagename);
        return (int) statement.executeInsert();
    }

    @Override
    public int delApp(String packagename, String activityname) {
        SQLiteStatement statement = null;
        switch (activityname){
            case "White" : statement = db.compileStatement("delete from "+Constants.TABLE_WHITE+" where packagename = ?");
                statement.bindString(1, packagename);
                break;

            case "Reminder" : statement = db.compileStatement("delete from "+Constants.TABLE_REMINDER+" where packagename = ?");
                statement.bindString(1, packagename);
                break;
        }
        return statement.executeUpdateDelete();
    }

//    @Override
//    public int updateApp(String name, String rule) {
//        SQLiteStatement statement = db.compileStatement("update "+Constants.TABLE_RULE+" set rule = ? where name = ?");
//        statement.bindString(1, rule);
//        statement.bindString(2, name);
//        return statement.executeUpdateDelete();
//    }


    @Override
    public String getAppByName(String name) {
        Cursor cursor = db.query(
                Constants.TABLE_WHITE,
                new String[]{"*"},
                "packagename=?",
                new String[]{name},
                null,
                null,
                null
        );
        if (cursor.moveToNext()){
            return cursor.getString(cursor.getColumnIndex("packagename"));
        }
        return null;
    }

    @SuppressLint("Recycle")
    @Override
    public List<String> getAllApp(String activityName) {
        List<String> list = new ArrayList<>();
        Cursor cursor = null;
        switch (activityName){
            case "White" : cursor = db.query(
                    Constants.TABLE_WHITE,
                    new String[]{"*"},
                    null,
                    null,
                    null,
                    null,
                    null
            );break;
            case "Reminder" : cursor = db.query(
                    Constants.TABLE_REMINDER,
                    new String[]{"*"},
                    null,
                    null,
                    null,
                    null,
                    null
            );break;
        }
        while (cursor.moveToNext()){
            list.add(cursor.getString(cursor.getColumnIndex("packagename")));
        }
        return list;
    }
}
