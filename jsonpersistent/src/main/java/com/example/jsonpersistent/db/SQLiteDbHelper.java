package com.example.jsonpersistent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * table 不要指定主键，sqlite会自己管理好
 *
 *
 */

public class SQLiteDbHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLiteDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "Persistent.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "oncreate");
        //we are not creating any tables when create
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //we will not upgrade the database
    }
}
