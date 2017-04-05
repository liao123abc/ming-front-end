package com.example.jsonpersistent;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.jsonpersistent.db.SQLiteDbHelper;
import com.example.jsonpersistent.db.SQLiteTableManager;
import com.example.jsonpersistent.db.contract.InfoTable;
import com.example.jsonpersistent.model.DataObject;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //检查是否需要：1.创建表  2.调整表--表结构有变更
        maintainTableStructure(dataObject);

        String tableName = dataObject.getTableName();
        //把数据插进去表里面
        ArrayList<HashMap<String, String>> records = dataObject.getRecords();
        Log.d(TAG, tableName.toString() + " " + records.toString());
        if (!sqliteTableManager.insertList2Table(tableName, records)) {
            return false;
        }
        return true;
    }

    /**
     * 1. 没有表则创建表
     * 2. 如果表结构有改变则调整表结构
     *
     * 没有改变表结构则不执行任何操作
     * @param dataObject
     */
    private void maintainTableStructure(DataObject dataObject) {
        String tableName = dataObject.getTableName();
        int newHashCode = getHashCode(tableName, dataObject.getColumnsInfo());
        //检查是否存在这个表，没有的话要先创建这个表
        if (!sqliteTableManager.containTable(tableName)) {
            //创建对象表
            sqliteTableManager.createTable(tableName, dataObject.getColumnsInfo());

            //往信息表插入一条数据
            HashMap<String, String> content = new HashMap<>();
            content.put(InfoTable.CODE, dataObject.getId());
            content.put(InfoTable.NAME, dataObject.getTableName());
            content.put(InfoTable.HASHCODE, newHashCode + "");
            sqliteTableManager.insertTable(InfoTable.TABLE_NAME, content);
        } else {
            //从信息表中取出一个数据，然后对比hashcode，判断表结构是否改变了，一般改变都是指增加了一个或者多个字段，
            //这时候需要alter表
            String oldHashCode = sqliteTableManager.getHashCodeFromInfoTable(dataObject.getId());

            //对比是否有变化
            if (!oldHashCode.equals(newHashCode + "")) {
                Log.d(TAG, "table : " + tableName + " is changed");
                //1. alter table
                sqliteTableManager.alterTable(tableName, dataObject.getColumnsInfo());
                //2. update hashcode record
                sqliteTableManager.updateInfoTableHashCode(tableName, newHashCode + "");
            }
        }
    }

    /**
     * 生成HashMap的一个hashcode
     * @param hashMap
     * @return
     */
    private int getHashCode(String tableName, HashMap<String, String> hashMap) {
        String target = tableName;
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            target += key;
            target += value;
        }
        return target.hashCode();
    }
}
