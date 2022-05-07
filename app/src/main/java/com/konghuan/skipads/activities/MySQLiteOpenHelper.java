package com.konghuan.skipads.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


import com.konghuan.skipads.bean.APP;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mySQLite";//数据库名字

    private String creat_table1 = "create table " + "Reminder" +" (id integer primary key autoincrement, bagname, appname, edition)";
    private String creat_table2 = "create table " + "WhiteList" +" (id integer primary key autoincrement, bagname, appname, edition)";
    private String creat_table3 = "create table " + "Self_Definder" +" (id integer primary key autoincrement, bagname, appname, edition)";
    private String creat_table4 = "create table " + "Times" +" (id integer primary key autoincrement, times)";

    public MySQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,1);
    }

    public MySQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creat_table1);
        db.execSQL(creat_table2);
        db.execSQL(creat_table3);
        db.execSQL(creat_table4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //添加数据
    public void insertData(APP app){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
//        values.put("img",app.getImg());
        values.put("name",app.getName());

        //添加在Reminder表
        db.insert("Reminder",null,values);
    }

    //删除数据
    public void delectByName(String name){
        SQLiteDatabase db = getWritableDatabase();

        db.delete("Reminder","name like ?",new String[]{name});
    }

    //查询数据
    public List<APP> queryAllFromDb(){

        SQLiteDatabase db = getWritableDatabase();
        List<APP> appList = new ArrayList<>();

        //查询
        Cursor cursor = db.query("Reminder",null,null,null,null,null,null);
        if (cursor != null){
            while (cursor.moveToNext()) {

                int img = cursor.getInt(cursor.getColumnIndex("img"));
                String name = cursor.getString(cursor.getColumnIndex("name"));

                APP app = new APP();
//                app.setImg(img);
                app.setName(name);

                appList.add(app);
            }
            cursor.close();
        }
        return appList;
    }

}
