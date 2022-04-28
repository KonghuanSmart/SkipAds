package com.konghuan.skipads.service;

import android.content.Context;

import com.konghuan.skipads.Result;
import com.konghuan.skipads.exception.LoginException;
import com.konghuan.skipads.service.Impl.AccountDaoImpl;

public class AccountService {

    private static AccountDaoImpl dao = new AccountDaoImpl();

    public Result login(Context context, String id, String password){
        return dao.login(context, id, password);
    }

    public void register(Context context, String name, String pass) throws LoginException {
        dao.register(context, name, pass);
    }


    public void resetPassword(Context context, String id, String newPassword) throws LoginException {
        dao.resetPassword(context, id, newPassword);
    }
}
