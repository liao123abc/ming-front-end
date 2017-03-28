package com.thomas.frontend.model.info;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thomasliao on 2017/3/24.
 *                   "offlinemodelcode": "629602839767531001", // 离线对象code
 *                    "offlinemodelname": "营销区域-离线模型" // 名称
 */

public class ObjectInfo {

    @SerializedName("offlinemodelcode")
    private String modelCode;

    @SerializedName("offlinemodelname")
    private String modelName;

    @Override
    public String toString() {
        return "ObjectInfo{" +
                "modelCode='" + modelCode + '\'' +
                ", modelName='" + modelName + '\'' +
                '}';
    }

    public String getModelCode() {
        return modelCode;
    }

    public String getModelName() {
        return modelName;
    }
}
