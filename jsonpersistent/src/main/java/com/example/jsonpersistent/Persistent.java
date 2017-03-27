package com.example.jsonpersistent;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.util.Log;

import com.example.jsonpersistent.db.SQLiteTableManager;
import com.example.jsonpersistent.model.DataObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * 将json保存到sqlite中
 *
 */
public class Persistent<T>{

    private static final String TAG = Persistent.class.getSimpleName();

    private SQLiteTableManager sqliteTableManager;
    private Context context;
    private DataObjectConverter converter;

    public Persistent(Context context, DataObjectConverter dataObjectConverter) {
        this.context = context;
        this.converter = dataObjectConverter;
    }

    public boolean add(T data) {

        DataObject dataObject = converter.serialize(data);
        String tableName = dataObject.getTableName();
        ArrayList<HashMap<String, String>> records = dataObject.getRecords();

        return sqliteTableManager.insertList2Table(tableName, records);
    }

    /**
     * update table according to the json
     * @param json
     * @return
     */
    public boolean updateTable(String json) {

        return false;
    }

    /**
     * parse JsonObject to Flat data , and save to db by property
     * @param json
     * @return
     */
    public boolean persistent(String json) {

        return false;
    }

}
