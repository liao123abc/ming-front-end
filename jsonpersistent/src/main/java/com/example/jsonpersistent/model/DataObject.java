package com.example.jsonpersistent.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomasliao on 2017/3/25.
 * 对应一张表, 和数个记录
 */

public class DataObject {
    private String tableName;
    private ArrayList<HashMap<String, String>> records;

    public String getTableName() {
        return tableName;
    }

    public ArrayList<HashMap<String, String>> getRecords() {
        return records;
    }
}
