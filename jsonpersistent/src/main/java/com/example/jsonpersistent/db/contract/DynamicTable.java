package com.example.jsonpersistent.db.contract;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomasliao on 2017/4/1.
 *
 * 根据参数，动态拼凑
 * 1.创建表的SQL
 * 2.删除表的SQL
 * 3.变更表的SQL
 */

public class DynamicTable implements BaseColumns {
    private static final String TAG = DynamicTable.class.getSimpleName();

    private static final String COMMA_SEP = ",";

    public static String getCreateSQL(String tableName, HashMap<String, String> columnsInfo) {
        String sql1 = "CREATE TABLE " + tableName + " (" + DynamicTable._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT "+ COMMA_SEP;
        String sql2 = " ";

        for (Map.Entry<String, String> entry : columnsInfo.entrySet()) {
            String column = entry.getKey();
            String typeScript = entry.getValue();
            sql2 += column + " ";
            sql2 += typeScript;
            sql2 +=  COMMA_SEP;
        }

        sql2 = sql2.substring(0, sql2.length() - 1); // 去掉最后的","
        String sql3 = " )";
        String sql = sql1 + sql2 + sql3;

        return sql;
    }

    /**
     * ex. ALTER TABLE LocalInfo ADD COLUMN column_def2 VARchar;
     * @param column
     * @return
     */
    public static String getAlterSQL(String tableName, String column, String typeScript) {
        String sql1 = "ALTER TABLE " + tableName + " ADD COLUMN " + column + " " + typeScript;
        return sql1;
    }
}
