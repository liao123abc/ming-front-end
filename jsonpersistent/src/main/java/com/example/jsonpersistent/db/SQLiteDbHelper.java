package com.example.jsonpersistent.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jsonpersistent.db.contract.InfoTable;

import java.util.List;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * 初始化的时候创建一个Info表
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InfoTable.CREATE_INFO_TABLE_SQL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(InfoTable.DELETE_INFO_TABLE_SQL);
        onCreate(db);
    }
}
