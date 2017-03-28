package com.thomas.frontend.model.data;

/**
 * Created by thomasliao on 2017/3/25.
 *
 *
 * 描述一个字段
 *
 *
 *  "columns":[
 {
 "name": "", // 字段名
 "type": "", // 字段类型
 "length": "", // 字段长度
 "pk": "",   // 主键
 "notNull": "" // 必填
 }
 */

public class Column {
    private String name;
    private String type;
    private String length;
    private String pk;
    private String notNull;

    public String getName() {
        return name;
    }
}
