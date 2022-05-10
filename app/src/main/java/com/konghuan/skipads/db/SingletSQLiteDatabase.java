package com.konghuan.skipads.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.konghuan.skipads.Constants;

public class SingletSQLiteDatabase extends SQLiteOpenHelper {
    private static SingletSQLiteDatabase singletSQLiteDatabase;

    private SingletSQLiteDatabase(Context context){
        super(context, Constants.DB_NAME,null, Constants.VERSION);
    }

    public static SingletSQLiteDatabase getSingletSQLiteDatabase(Context context){
        if (singletSQLiteDatabase == null){
            synchronized (SingletSQLiteDatabase.class){
                if (singletSQLiteDatabase == null){
                    singletSQLiteDatabase = new SingletSQLiteDatabase(context);
                }
            }
        }
        return singletSQLiteDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_ACCOUNT);
        db.execSQL(Constants.CREATE_TABLE_RULE);
        db.execSQL(Constants.CREATE_TABLE_WHITE);
        db.execSQL(Constants.CREATE_TABLE_REMINDER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
