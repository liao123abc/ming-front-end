package com.thomas.frontend.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by thomasliao on 2017/3/25.
 */

public class TableData {
    @SerializedName("resp_data")
    private ArrayList<ConcreteData> concreteData;

    public ArrayList<ConcreteData> getConcreteData() {
        return concreteData;
    }
}
