package com.konghuan.skipads.service.Impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.Dao.AccountDao;
import com.konghuan.skipads.Result;
import com.konghuan.skipads.db.SingletSQLiteDatabase;
import com.konghuan.skipads.exception.LoginException;
import com.konghuan.skipads.utils.Encrypt;

public class AccountDaoImpl implements AccountDao {

    private static AccountDaoImpl dao;
    private static SingletSQLiteDatabase db;

    public Result login(Context context, String id, String password){
        final AccountDao dataBase = AccountDaoImpl.getAccountDaoImpl(context);
        try (Cursor cursor = dataBase.select(new String[]{"password","salt"},
                     "id=?",
                     new String[]{id},
                     null,
                     null,
                     null)) {
            Result result;
            if (cursor.moveToNext()) {
                if (cursor.getString(0).equals(Encrypt.encrypt(password, cursor.getString(1)))) {
                    result = new Result(Constants.LOGIN_SUCCESS, "登录成功", null);
                } else {
                    result = new Result(Constants.ERROR_PWD, "账号或密码错误", null);
                }
            } else {
                result = new Result(Constants.NULL_USER, "账号不存在", null);
            }
            return result;
        }
    }



    public void register(Context context, String id, String password) throws LoginException{
        final AccountDao dataBase = AccountDaoImpl.getAccountDaoImpl(context);
        try(Cursor cursor = dataBase.select(new String[]{"id"},
                "id=?",
                new String[]{id},
                null,
                null,
                null)){
            if (cursor.moveToNext()){
                throw new LoginException(Constants.ACCOUNT_EXIST, "用户已存在！");
            }else {
                String salt = Encrypt.getRandomString(8);
                dataBase.insert(id, Encrypt.encrypt(password, salt), salt);
            }
        }
    }


    public void resetPassword(Context context,String id, String newPassword) throws LoginException {
        final AccountDao dataBase = AccountDaoImpl.getAccountDaoImpl(context);
        int num = dataBase.update(id,
                newPassword,
                "id=?",
                new String[]{id});
        if (num != 1){
            throw new LoginException(Constants.NULL_ACCOUNT, "重设密码失败，账户不存在！");
        }
    }

    public AccountDaoImpl(){

    }

    private static AccountDaoImpl getAccountDaoImpl(Context context) {
        if (dao == null){
            synchronized (AccountDaoImpl.class){
                if (dao == null){
                    dao = new AccountDaoImpl();
                    db = SingletSQLiteDatabase.getSingletSQLiteDatabase(context);
                }
            }
        }
        return dao;
    }


    @Override
    public long insert(String id, String password, String salt) {

        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("password", password);
        values.put("salt", salt);
        sqLiteDatabase.beginTransaction();
        long num = sqLiteDatabase.insert(Constants.TABLE_ACCOUNT,null, values);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return num;

    }

    @Override
    public int delete(String whereClause, String[] whereArgs) {
        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.beginTransaction();
        int num = sqLiteDatabase.delete(Constants.TABLE_ACCOUNT,whereClause, whereArgs);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return num;

    }

    @Override
    public int update(String id, String password, String whereClause, String[] whereArgs) {
        final SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("password", password);
        sqLiteDatabase.beginTransaction();
        int num = sqLiteDatabase.update(Constants.TABLE_ACCOUNT,values,whereClause,whereArgs);
        sqLiteDatabase.setTransactionSuccessful();
        sqLiteDatabase.endTransaction();
        return num;
    }

    @Override
    public Cursor select(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return db.getWritableDatabase().query(Constants.TABLE_ACCOUNT, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

}
