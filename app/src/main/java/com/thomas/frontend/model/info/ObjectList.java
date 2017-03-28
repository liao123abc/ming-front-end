package com.thomas.frontend.model.info;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by thomasliao on 2017/3/23.
 */

public class ObjectList {

    @SerializedName("resp_data")
    private ArrayList<ObjectInfo> objectInfos;

    @Override
    public String toString() {
        return "ObjectList{" +
                "objectInfos = " + objectInfos +
                '}';
    }

    public String getObjectName(String code) {
        if (code == null || objectInfos == null) {
            return null;
        }
        for (ObjectInfo objectInfo : objectInfos) {
            if (objectInfo.getModelCode().equals(code)) {
                return objectInfo.getModelName();
            }
        }
        return null;
    }
}
