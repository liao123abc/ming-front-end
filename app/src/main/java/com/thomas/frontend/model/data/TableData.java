package com.thomas.frontend.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by thomasliao on 2017/3/25.
 *
 * 参考offline.md 一个tabledata对应于多个表和数据
 *
 * 一个concreteData对应一张表的数据
 *
 */

public class TableData {
    @SerializedName("resp_data")
    private ArrayList<ConcreteData> concreteData;

    public ArrayList<ConcreteData> getConcreteData() {
        return concreteData;
    }
}
