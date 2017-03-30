package com.thomas.frontend.model.data;

/**
 * Created by thomasliao on 2017/3/25.
 *
 *
 * 描述一个字段
 *
 *
 {
 "name": "b", // 字段名
 "typescript": "TEXT" // 字段类型脚本(sqlite语法脚本)
 },
 */

public class Column {
    private String name;
    private String typescript;// 字段类型脚本(sqlite语法脚本)

    public String getName() {
        return name;
    }
}
