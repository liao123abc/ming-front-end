package com.example.jsonpersistent.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomasliao on 2017/3/25.
 */

public class DataObject {
    private String tableName;
    private ArrayList<String> propertyNames;
    private ArrayList<HashMap<String, String>> records;

    public String getTableName() {
        return tableName;
    }

    public ArrayList<String> getPropertyNames() {
        return propertyNames;
    }

    public ArrayList<HashMap<String, String>> getRecords() {
        return records;
    }
}
