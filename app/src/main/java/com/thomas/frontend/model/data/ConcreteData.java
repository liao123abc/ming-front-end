package com.thomas.frontend.model.data;

import com.google.gson.annotations.SerializedName;
import com.thomas.frontend.model.data.MetaData;
import com.thomas.frontend.model.data.Record;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by thomasliao on 2017/3/25.
 */

public class ConcreteData {
    @SerializedName("metadata")
    private MetaData metaData;

    /**
     * 因为不知道record的key，所以用Map来存储
     */
    @SerializedName("records")
    private ArrayList<HashMap<String, String>> record;


    public HashMap<String, String> getRecord() {
        for (HashMap<String, String> map : record) {

        }
        return null;
    }
}
