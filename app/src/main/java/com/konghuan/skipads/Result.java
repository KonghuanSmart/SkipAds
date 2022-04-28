package com.konghuan.skipads;

import org.json.JSONObject;

public class Result {
    int statusCode;
    String message;
    JSONObject origin;
    public Result(int statusCode, String message, JSONObject origin) {
        this.statusCode = statusCode;
        this.message = message;
        this.origin = origin;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public JSONObject getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        return "Result{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", origin=" + origin +
                '}';
    }
}
