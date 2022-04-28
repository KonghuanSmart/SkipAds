package com.konghuan.skipads.entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.konghuan.skipads.Constants;
import com.konghuan.skipads.Result;
import com.konghuan.skipads.exception.LoginException;
import com.konghuan.skipads.service.AccountService;

public class Account implements LoginAccount{

    private final String id;
    private String password;
    private String userName;

    private static final AccountService service = new AccountService();

    private Account(String id, String password){
        this.id = id;
        this.password = password;
        userName = id;
    }

    public static Account createAccount(String id, String password){
        return new Account(id, password);
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void login(Context context) throws LoginException {
        Result result = service.login(context, id, password);
        if (result.getStatusCode() == Constants.LOGIN_SUCCESS){
        }else {
            throw new LoginException(result.getStatusCode(), result.getMessage());
        }
    }

    @Override
    public void logout(Context context) {
        SharedPreferences spf = context.getSharedPreferences("spfRecord", Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor edit = spf.edit();
        edit.putString("account", null);
        edit.putString("password", null);
        edit.putBoolean("isLogin", false);
        edit.putBoolean("isRemember", false);
    }


    @Override
    public void resetPassword(Context context, String newPassword) throws LoginException {
        if (newPassword.equals(this.password)){
            throw new LoginException(Constants.SAME_PWD,"新密码不能与旧密码相同！");
        }
        service.resetPassword(context, this.id, newPassword);
    }
}
