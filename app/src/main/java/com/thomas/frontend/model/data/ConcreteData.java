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

    private ArrayList<HashMap<String, String>> records;





}
