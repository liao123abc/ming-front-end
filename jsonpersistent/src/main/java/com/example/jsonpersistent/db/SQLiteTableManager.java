package com.example.jsonpersistent.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jsonpersistent.db.contract.DynamicTable;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * this class manager the tables including:
 * 1.add new table(never delete table)
 * 2.alert table(add property, never delete property, abandon property left blank data)
 * 3.
 */

public class SQLiteTableManager implements Closeable{
    private static final String TAG = SQLiteTableManager.class.getSimpleName();
    private SQLiteOpenHelper sqLiteOpenHelper;
    private SQLiteDatabase db;
    private List<String> currentTables;

    public SQLiteTableManager(SQLiteDbHelper dbHelper) {
        this.sqLiteOpenHelper = dbHelper;
        this.db = dbHelper.getWritableDatabase();
        Log.d(TAG, db.toString());
        currentTables = getTableNames();
    }

    public DynamicTable createTable(String tableName, List<String> properties, List<String> typeScripts) {
        DynamicTable table = new DynamicTable(tableName, properties, typeScripts);
        String sql = table.getCreateSQL();
        if (sql != null) {
            db.execSQL(sql);
            currentTables.add(tableName);
            return table;
        }
        return null;
    }

    public void dropTable(String tableName) {
        String SQL_DELETE_TABLE = "DROP TABLE IF EXISTS " + tableName;
        db.execSQL(SQL_DELETE_TABLE);
    }

    /**
     * 获取db的所有表的名字
     * @return
     */
    private ArrayList<String> getTableNames() {
        ArrayList<String> tables = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM sqlite_master WHERE type='table';", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String tableName = cursor.getString(1);
            if (!tableName.equals("android_metadata") &&
                    !tableName.equals("sqlite_sequence"))
                tables.add(tableName);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d(TAG, "tables: " + tables.toString());
        return tables;
    }

    public boolean containTable(String tableName) {
        for (String string : currentTables) {
            if (string.equals(tableName)) {
                return true;
            }
        }
        return false;
    }

    public boolean insertList2Table(String tableName, ArrayList<HashMap<String, String>> list) {
        for (HashMap hashMap : list) {
            if (!insertTable(tableName, hashMap)) {
                Log.d(TAG, "fail to insert to table " + tableName);
                return false;
            }
        }
        return true;
    }

    public boolean insertTable(String tableName, HashMap<String, String> hashMap) {
        if (!containTable(tableName)) {
            Log.e(TAG, "attempting to insert into non-exist table " + tableName);
            return false;
        }

        return insert(tableName, hashMap);
    }

    public boolean insert(String tableName, HashMap<String, String> hashMap) {
        long rowId = -1;
        ContentValues cv = new ContentValues();
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            cv.put(key, value);
        }
        try {
            rowId = db.insert(tableName, null, cv);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        if (rowId == -1) {
            return false;
        }

        return true;
    }

    private String[] getColumnNames(String tableName) {
        Cursor dbCursor = db.query(tableName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        return columnNames;
    }

    @Override
    public void close() throws IOException {
        if (db != null) {
            db.close();
        }
    }

    public void open() {
        if (!db.isOpen()) {
            db = this.sqLiteOpenHelper.getWritableDatabase();
        }
    }
}
