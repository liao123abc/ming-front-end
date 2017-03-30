package com.thomas.frontend.model.data;

import com.google.gson.annotations.SerializedName;
import com.thomas.frontend.model.data.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by thomasliao on 2017/3/25.
 *
 * 一个ConcreteData对应一张表和数据, 也对应一个业务对象
 * 存放着表的信息， record存放着数据
 * 对字段的操作
 * 增：alter Table add column
 * 删：不用处理，服务器返回来的数据没有这个字段，手机表对应该字段的值为null
 * 改：allin为1， 删除整个本地表在重新建该表
 * 查：~~~ 不存在这个操作，服务器不会查询手机端的字段
 *
 */

public class ConcreteData {

    private String allin; // 1 删本地表再新建，全量处理   0 按照正常增量逻辑处理

    @SerializedName("objectcode")
    private String code;
    @SerializedName("tablename")
    private String tableName;

    private ArrayList<Column> columns;

    /**
     * 因为不知道record的key，所以用Map来存储
     */
    @SerializedName("records")
    private ArrayList<HashMap<String, String>> records;

    public ArrayList<String> getAllColumnsName(){
        ArrayList<String> list = new ArrayList<>();
        for (Column column : columns) {
            list.add(column.getName());
        }
        return list;
    }

    /**
     * 获取业务对象的code
     * @return
     */
    public String getObjectCode() {
        return code;
    }

    public ArrayList<HashMap<String, String>> getRecords() {
        return records;
    }

    public boolean isAllIn() {
        return allin.equals("1");
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
