package com.example.jsonpersistent.db;

import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * Created by thomasliao on 2017/3/23.
 */

public class SQLiteTableManager implements Closeable{
    private final SQLiteDatabase db;

    private List<String> tables;

    public SQLiteTableManager(SQLiteDbHelper dbHelper) {
        this.db = dbHelper.getWritableDatabase();
    }

    public void updateTables(List<String> tables) {

    }

    @Override
    public void close() throws IOException {
        if (db != null) {
            db.close();
        }
    }
}
