package com.konghuan.skipads.Dao;

import android.database.Cursor;

/**
 * 用户表Dao接口，包含用户表的增删改删查
 */
public interface AccountDao {
    /**
     *用户插入方法
     * @param id
     * @param password
     * @return 返回插入成功行数
     */
    long insert(String id, String password, String salt);

    /**
     * 用户删除方法
     * @param whereClause
     * @param whereArgs
     * @return 返回删除成功行数
     */
    int delete(String whereClause,String[] whereArgs);

    /**
     * 用户修改方法
     * @param id
     * @param password
     * @param whereClause
     * @param whereArgs
     * @return 返回修改成功行数
     */
    int update(String id, String password,String whereClause,String[] whereArgs);

    /**
     * 用户查询方法
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @return 返回查询结果游标
     */
    Cursor select(String[] columns, String selection, String[] selectionArgs, String groupBy,String having,String orderBy);

}
