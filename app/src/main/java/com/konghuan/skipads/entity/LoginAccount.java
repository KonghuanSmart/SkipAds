package com.konghuan.skipads.entity;

import android.content.Context;

import com.konghuan.skipads.exception.LoginException;

/**
 * 登录账户接口，包含登陆相关的操作
 */
public interface LoginAccount {
    /**
     * 对账户进行登录操作
     *
     * @throws LoginException 登陆过程可能出现任何异常
     */
    void login(Context context) throws LoginException;

    /**
     * 对账户进行登出操作
     *
     * @throws LoginException 登出过程中可能出现任何异常
     */
    void logout(Context context) throws LoginException;

    /**
     * 重置账户密码（重置后，token会失效，需要重新登陆）
     * @param newPassword 新密码（不能和原密码相同，否则会失败）
     * @throws LoginException 重置过程中可能出现任何异常
     */
    void resetPassword(Context context, String newPassword) throws LoginException;
}