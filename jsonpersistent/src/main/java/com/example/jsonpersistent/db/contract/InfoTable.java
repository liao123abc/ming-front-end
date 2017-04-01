package com.example.jsonpersistent.db.contract;

import android.provider.BaseColumns;

/**
 * Created by thomasliao on 2017/4/1.
 */

public class InfoTable implements BaseColumns {

    private static final String TABLE_NAME = "LocalInfo";

    private static final String CODE = "code";
    private static final String NAME = "name";
    private static final String HASHCODE = "hashcode";

    private static final String COMMA_SEP = ",";
    private static final String VAR_TYPE = " VAR ";

    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " (" +
            InfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
            NAME + VAR_TYPE + COMMA_SEP + HASHCODE + VAR_TYPE + " )";

    public static final String SQL_DELETE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
