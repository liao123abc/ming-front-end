package com.example.jsonpersistent.db.contract;

import android.provider.BaseColumns;

import java.util.HashMap;

/**
 * Created by thomasliao on 2017/4/1.
 * 保存一下信息：
 * 表的id， 表的名字， 表的hashCode(由表的列名和类型脚本生成)
 */

public class InfoTable implements BaseColumns {

    public static final String TABLE_NAME = "LocalInfo";

    public static final String CODE = "code";
    public static final String NAME = "name";
    public static final String HASHCODE = "hashcode";

    private static final String COMMA_SEP = ",";
    private static final String VAR_TYPE = " VAR ";

    public static final String CREATE_INFO_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " (" +
            InfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
            CODE + VAR_TYPE + COMMA_SEP +
            NAME + VAR_TYPE + COMMA_SEP +
            HASHCODE + VAR_TYPE + " )";

    public static final String DELETE_INFO_TABLE_SQL =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
