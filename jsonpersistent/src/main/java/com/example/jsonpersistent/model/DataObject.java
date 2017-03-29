package com.example.jsonpersistent.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomasliao on 2017/3/25.
 * 对应一张表, 和一个或者数个记录
 */

public class DataObject {
    private String tableName;
    private ArrayList<String> properties;
    private ArrayList<HashMap<String, String>> records;//一个或者多个记录

    public String getTableName() {
        return tableName;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public ArrayList<HashMap<String, String>> getRecords() {
        return records;
    }

    public DataObject(String tableName, ArrayList<String> properties, ArrayList<HashMap<String, String>> records) {
        this.tableName = tableName;
        this.properties = properties;
        this.records = records;
    }
}
