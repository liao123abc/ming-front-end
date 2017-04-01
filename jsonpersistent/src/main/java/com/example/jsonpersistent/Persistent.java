package com.example.jsonpersistent;

import android.content.Context;
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

            boolean rebuild = dataObject.isRebuild();

            /**
             * 如果需要rebuild， 那么要先删除表，然后再重新建表
             */
            if (rebuild) {
                deleteTable(tableName);
            }

            //会先检查是否有这个表，没有就先建表再插数据
            if (!persistent(dataObject)) {
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

    /**
     * 删除表
     */
    private void deleteTable(String tableName) {
        sqliteTableManager.dropTable(tableName);
    }

    /**
     *  持久化数据
     *  如果没有表，那么先要建表
     *  如果已经有表，直接插入
     * @param dataObject
     * @return
     */
    private boolean persistent(DataObject dataObject) {
        String tableName = dataObject.getTableName();
        //检查是否存在这个表，没有的话要先创建这个表
        if (!sqliteTableManager.containTable(tableName)) {
            List columns = dataObject.getColumns();
            List columnsTypeScript = dataObject.getColumnTypeScript();
            sqliteTableManager.createTable(tableName, columns, columnsTypeScript);
        }
        //把数据插进去表里面
        ArrayList<HashMap<String, String>> records = dataObject.getRecords();
        Log.d(TAG, tableName.toString() + " " + records.toString());
        if (!sqliteTableManager.insertList2Table(tableName, records)) {
            return false;
        }
        return true;
    }
}
