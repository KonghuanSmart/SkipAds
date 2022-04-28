package com.konghuan.skipads.exception;

public class LoginException extends SkipAdsException{
    public LoginException(int statusCode, String msg){
        super(statusCode, msg);
    }
}
