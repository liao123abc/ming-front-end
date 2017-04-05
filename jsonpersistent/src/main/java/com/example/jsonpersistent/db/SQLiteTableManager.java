package com.example.jsonpersistent.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.jsonpersistent.db.contract.DynamicTable;
import com.example.jsonpersistent.db.contract.InfoTable;

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

    public void createTable(String tableName, HashMap<String, String> columnsInfo) {
        String sql = DynamicTable.getCreateSQL(tableName, columnsInfo);
        if (sql != null) {
            db.execSQL(sql);
            currentTables.add(tableName);
        }
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

    /**
     * 根据code从信息表中获取hashcode
     * @param code
     * @return
     */
    public String getHashCodeFromInfoTable(String code) {
        String[] projection = {InfoTable.HASHCODE};
        String selection = InfoTable.CODE + " = ?";
        String[] selectionArgs = {code};
        Cursor cursor = db.query(InfoTable.TABLE_NAME, projection, selection, selectionArgs, null,
                null, null);
        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            cursor.close();
            return null;
        }
        String value = cursor.getString(0);
        return value;
    }

    /**
     * 增加列
     * @param tableName
     * @param columnsInfo
     */
    public void alterTable(String tableName, HashMap<String, String> columnsInfo) {
        //1.find the newly added extra columns
        HashMap<String, String> exColumns = getDifferInfo(tableName, columnsInfo);

        //2.alter the table, 遍历增加列，sqlite每次只能增加一个列
        for (Map.Entry<String, String> entry : exColumns.entrySet()) {
            String sql = DynamicTable.getAlterSQL(tableName, entry.getKey(), entry.getValue());
            db.execSQL(sql);
        }
    }

    /**
     * 根据name更新hashcode
     * @param name
     * @param newCode
     */
    public void updateInfoTableHashCode(String name, String newCode) {
        // New value for one column
        ContentValues values = new ContentValues();
        values.put(InfoTable.HASHCODE, newCode);

        // Which row to update, based on the title
        String selection = InfoTable.NAME + " LIKE ?";
        String[] selectionArgs = { name };

        int count = db.update(
                InfoTable.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        if (count > 1) {
            Log.e(TAG,  " hash code of " + name + " is more than 1!");
        } else {
            Log.d(TAG, name + "\'s hashcode update successfully ! ");
        }
    }

    /**
     * 找出来增加的列
     * @param tableName
     * @param newColumnsInfo
     * @return
     */
    public HashMap<String, String> getDifferInfo(String tableName,
                                                 HashMap<String, String> newColumnsInfo) {
        HashMap<String, String> exColumns = new HashMap<>();

        List<String> list = getColumnNames(tableName);
        for (Map.Entry<String, String> entry : newColumnsInfo.entrySet()) {
            String key = entry.getKey();
            if (!isKeyInList(key, list)) {
                exColumns.put(key, entry.getValue());
            }
        }

        return exColumns;
    }

    private boolean isKeyInList(String key, List<String> list) {
        for (String str : list) {
            if (str.equals(key)) {
                return true;
            }
        }
        return false;
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

    private List getColumnNames(String tableName) {
        Cursor dbCursor = db.query(tableName, null, null, null, null, null, null);
        String[] columnNames = dbCursor.getColumnNames();
        List list = new ArrayList<String>();
        for (String str : columnNames) {
            list.add(str);
        }
        return list;
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
