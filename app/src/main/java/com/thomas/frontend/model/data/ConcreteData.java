package com.thomas.frontend.model.data;

import com.google.gson.annotations.SerializedName;
import com.thomas.frontend.model.data.MetaData;
import com.thomas.frontend.model.data.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by thomasliao on 2017/3/25.
 *
 * 一个ConcreteData对应一张表和数据, 也对应一个业务对象
 * Metadata里面存放着表的信息， record存放着数据
 *
 *
 */

public class ConcreteData {
    @SerializedName("metadata")
    private MetaData metaData;

    /**
     * 因为不知道record的key，所以用Map来存储
     */
    @SerializedName("records")
    private ArrayList<HashMap<String, String>> records;

    /**
     * 获取业务对象的code
     * @return
     */
    public String getObjectCode() {
        return metaData.getCode();
    }

    public ArrayList<HashMap<String, String>> getRecords() {
        return records;
    }

    public ArrayList<String> getAllColumnsName() {
        return metaData.getColumnNames();
    }

    /**
     * 校验是否有重复key
     * @throws KeyDuplicateException
     */
    private void validationDuplicate() throws KeyDuplicateException{
        //正常来说，record里面所有HashMap里面的key都是唯一的，如果不唯一，那么我们要报错\
        for (HashMap<String, String> map : records) {
            HashMap<String, String> hashMap = new HashMap<>();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (hashMap.get(key) != null) {//suppose to be null before add
                    throw new KeyDuplicateException(key + "is duplicated");
                } else {
                    hashMap.put(key, value);
                }
            }
        }
    }
    
    private void validateAbsent() {
        //// TODO: 2017/3/29 通过对比metadata中的可以和数据中的可以，校验是否有部分key缺失
    }
}
