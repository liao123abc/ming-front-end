package com.thomas.frontend.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by thomasliao on 2017/3/25.
 * {
 "resp_data":[
 {
 "metadata":{
 "offlinemodelcode": "001", //对象名
 "columns":[
 {
 "name": "", // 字段名
 "type": "", // 字段类型
 "length": "", // 字段长度
 "pk": "",   // 主键
 "notNull": "" // 必填
 }
 ]
 },
 "records":[
 {
 "name": "1",
 "type": "online",
 "length": "10",
 "pk": "111",
 "notNull": "嘿嘿"
 */

public class MetaData {
    @SerializedName("offlinemodelcode")
    private String code;

    private ArrayList<Column> columns;

    public String getCode() {
        return code;
    }

    public ArrayList<String> getColumnNames(){
        ArrayList<String> list = new ArrayList<>();
        for (Column column : columns) {
            list.add(column.getName());
        }
        return list;
    }
}
