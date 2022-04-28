package com.konghuan.skipads;

public class Constants {
    public static String APP_NAME = "com.konghuan.skipads";
    public static String DB_NAME = "skip_ads.db";
    public static String SHARE_NAME = "settings";
    public static String TAG_TAIL = "-SkipAdsTAG";
    public static String RECORD_INFO = "record_info";
    public static int VERSION = 1;
    public static int LOGIN_SUCCESS = 1001;
    public static int ERROR_PWD = 1002;
    public static int NULL_USER = 1003;
    public static int SAME_PWD = 1004;
    public static int NULL_ACCOUNT = 1005;
    public static int ACCOUNT_EXIST = 1006;

    public static final String TABLE_ACCOUNT = "account";
    public static final String TABLE_PACKAGE_NAME = "package_name";
    public static final String TABLE_RULE = "rule";

    public static final String CREATE_TABLE_ACCOUNT =
            "CREATE TABLE "+TABLE_ACCOUNT+"(id varchar(255) PRIMARY KEY NOT NULL, " +
            "password varchar(255) NOT NULL, " +
            "salt varchar(255) NULL)";

    public static final String CREATE_TABLE_RULE =
            "CREATE TABLE "+TABLE_RULE+"(name varchar(255) NOT NULL PRIMARY KEY, " +
            "rule varchar(255) NOT NULL);";
}
