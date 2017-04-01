package com.example.jsonpersistent.db.contract;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.List;

/**
 * Created by thomasliao on 2017/4/1.
 */

public class DynamicTable implements BaseColumns {
    private static final String TAG = DynamicTable.class.getSimpleName();
    private String tableName;
    private List<String> properties;
    private List<String> typeScripts;
    private static final String COMMA_SEP = ",";

    public DynamicTable(String tableName, List<String> properties, List<String> typeScripts) {
        this.tableName = tableName;
        this.properties = properties;
        this.typeScripts = typeScripts;
    }

    public String getCreateSQL() {
        if (properties.size() != typeScripts.size()) {
            Log.e(TAG, "properties size not equal to typeScripts size");
            return null;
        }
        String sql1 = "CREATE TABLE " + tableName + " (" + this._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT "+ COMMA_SEP;
        String sql2 = " ";

        for (int i = 0; i < properties.size(); i++) {
            sql2 += properties.get(i) + " ";
            sql2 += typeScripts.get(i);
            sql2 +=  COMMA_SEP;
        }

        sql2 = sql2.substring(0, sql2.length() - 1); // 去掉最后的","
        String sql3 = " )";
        String sql = sql1 + sql2 + sql3;

        return sql;
    }
}
