package com.konghuan.skipads.service.Impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.Dao.RuleDao;
import com.konghuan.skipads.db.SingletSQLiteDatabase;
import com.konghuan.skipads.entity.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleDaoImpl implements RuleDao {

    private static SQLiteDatabase db;
    private final Context mContext;

    public RuleDaoImpl(Context mContext) {
        this.mContext = mContext;
        if (db == null){
            synchronized (RuleDaoImpl.class){
                if (db == null){
                    db = SingletSQLiteDatabase.getSingletSQLiteDatabase(mContext).getWritableDatabase();
                }
            }
        }
    }


    @Override
    public int addRule(String name, String rule) {
        SQLiteStatement statement = db.compileStatement("insert into "+Constants.TABLE_RULE+"(name, rule) values(?, ?)");
        statement.bindString(1, name);
        statement.bindString(2, rule);
        return (int) statement.executeInsert();
    }

    @Override
    public int updateRule(String name, String rule) {
        SQLiteStatement statement = db.compileStatement("update "+Constants.TABLE_RULE+" set rule = ? where name = ?");
        statement.bindString(1, rule);
        statement.bindString(2, name);
        return statement.executeUpdateDelete();
    }

    @Override
    public Rule getRuleByName(String name) {
        Cursor cursor = db.query(
                Constants.TABLE_RULE,
                new String[]{"*"},
                "name=?",
                new String[]{name},
                null,
                null,
                null
        );
        if (cursor.moveToNext()){
            return (new Rule(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("rule"))));
        }
        return null;
    }

    @Override
    public int delRule(String name) {
        SQLiteStatement statement = db.compileStatement("delete from "+Constants.TABLE_RULE+" where rid = ?");
        statement.bindString(1, name);
        return statement.executeUpdateDelete();
    }

    @SuppressLint("Recycle")
    @Override
    public Map<String, String> getAllRule() {
        Map<String, String> map = new HashMap<>();
        Cursor cursor = db.query(
                Constants.TABLE_RULE,
                new String[]{"*"},
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()){
            map.put(cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("rule")));
        }
        return map;
    }


}
