package com.example.jsonpersistent;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.util.Log;

import com.example.jsonpersistent.db.SQLiteDbHelper;
import com.example.jsonpersistent.db.SQLiteTableManager;
import com.example.jsonpersistent.model.DataObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * 将json保存到sqlite中
 *
 */
public class Persistent<T> implements Closeable{

    private static final String TAG = Persistent.class.getSimpleName();

    private SQLiteTableManager sqliteTableManager;
    private Context context;
    private DataObjectConverter converter;

    public Persistent(Context context, DataObjectConverter dataObjectConverter) {
        this.context = context;
        this.converter = dataObjectConverter;
        sqliteTableManager = new SQLiteTableManager(new SQLiteDbHelper(context));
    }

    public boolean add(T data) {

        List<DataObject> list = converter.serialize(data);
        for (DataObject dataObject : list) {
            String tableName = dataObject.getTableName();

            //检查是否存在这个表，没有的话要先创建这个表
            if (!sqliteTableManager.containTable(tableName)) {
                sqliteTableManager.createTable(tableName, dataObject.getProperties());
            }
            //把数据插进去表里面
            ArrayList<HashMap<String, String>> records = dataObject.getRecords();
            Log.d(TAG, tableName.toString() + " " + records.toString());
            if (!sqliteTableManager.insertList2Table(tableName, records)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void close() throws IOException {
        sqliteTableManager.close();
    }

    /**
     * 只内部使用
     */
    protected void open() {
        sqliteTableManager.open();
    }
}
