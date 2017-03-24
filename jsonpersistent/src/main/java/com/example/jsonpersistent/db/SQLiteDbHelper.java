package com.example.jsonpersistent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thomasliao on 2017/3/23.
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JsonPersistent.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
