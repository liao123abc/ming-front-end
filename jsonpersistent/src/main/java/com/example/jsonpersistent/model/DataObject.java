package com.example.jsonpersistent.model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomasliao on 2017/3/25.
 * 对应一张表, 和一个或者数个记录
 */

public class DataObject {
    private String tableName;
    private String description;//object name
    private String id;
    private boolean rebuild;
    private HashMap<String, String> columnsInfo = new HashMap<>();
    private ArrayList<HashMap<String, String>> records;//一个或者多个记录
    public String getTableName() {
        return tableName;
    }

    public ArrayList<HashMap<String, String>> getRecords() {
        return records;
    }

    public boolean isRebuild() {
        return rebuild;
    }

    public HashMap<String, String> getColumnsInfo() {
        return columnsInfo;
    }

    public String getId() {
        return id;
    }

    /**
     *
     * @param tableName  表名
     * @param description 对象名
     * @param rebuild    是否需要删除该表，然后重新创建一个新表
     * @param columns    列的名字
     * @param columnTypeScript  列的类型
     * @param records    数据记录
     */
    public DataObject(String id,
                      String tableName,
                      String description,
                      boolean rebuild,
                      ArrayList<String> columns,
                      ArrayList<String> columnTypeScript,
                      ArrayList<HashMap<String, String>> records) {
        this.id = id;
        this.tableName = tableName;
        this.description = description;
        this.rebuild = rebuild;
        for (int i = 0; i < columns.size(); i++) {
            columnsInfo.put(columns.get(i), columnTypeScript.get(i));
        }
        this.records = records;
    }
}
