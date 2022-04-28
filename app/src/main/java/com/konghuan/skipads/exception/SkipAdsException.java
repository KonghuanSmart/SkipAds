package com.konghuan.skipads.exception;

public class SkipAdsException extends Exception{
    public SkipAdsException(int statusCode, String msg){
        super("错误码: "+statusCode+" > "+msg);
    }
}
