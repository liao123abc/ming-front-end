package com.thomas.frontend.persistent;

import android.content.Context;

import com.example.jsonpersistent.DataObjectConverter;
import com.example.jsonpersistent.PersistenceManager;
import com.example.jsonpersistent.Persistent;
import com.example.jsonpersistent.model.DataObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thomas.frontend.model.data.ConcreteData;
import com.thomas.frontend.model.data.TableData;
import com.thomas.frontend.model.info.ObjectList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by thomasliao on 2017/3/25.
 */

public class PersistentSample {
    private static final String TAG = PersistentSample.class.getSimpleName();
    private static final String ADDITIONAL_STATUS = "localstatus";
    private static final String ADDITIONAL_UPDATETIME = "localupdatetime";
    private static final String ADDITIONAL_STATUS_TYPE = "INT";
    private static final String ADDITIONAL_UPDATETIME_TYPE = "LONG";

    public void test(Context context, String info, String content) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("info", info);
        hashMap.put("content", content);
        GsonBuilder builder = new GsonBuilder();
        Gson o = builder.create();

        MyDataObjectConverter converter = new MyDataObjectConverter(o);
        Persistent persistent = PersistenceManager.openPersistent(context, converter);
        persistent.add(hashMap);
        try {
            persistent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * custom Converter
     */
    class MyDataObjectConverter implements DataObjectConverter<HashMap<String, String>> {

        private final Gson gson;

        public MyDataObjectConverter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public HashMap<String, String> deserialize(List<DataObject> value) {
            //TBD
            return null;
        }

        @Override
        public List<DataObject> serialize(HashMap<String, String> hashMap) {
            //获取json格式的原始数据
            String infoJson = hashMap.get("info");
            String contentJson = hashMap.get("content");
            if (infoJson != null && !infoJson.equals("")
                    && contentJson != null && !contentJson.equals("")
                    ) {
                //Gson parse json
                TableData tableData = gson.fromJson(contentJson, TableData.class);
                ObjectList objectList = gson.fromJson(infoJson, ObjectList.class);//对象列表

                //从解析出来的数据中加工成需要的格式，给到persistent层
                List<DataObject> list = constructDataList(tableData, objectList);
                return list;
            }

            return null;
        }

        private List<DataObject> constructDataList(TableData tableData, ObjectList objectList) {
            ArrayList<ConcreteData> list = tableData.getConcreteData();

            ArrayList<DataObject> resultList = new ArrayList<DataObject>();
            for (ConcreteData concreteData : list) {//一个concreteData对应于一张表：表名 + 一条或者多条记录
                DataObject dataObject = constructDataObject(concreteData, objectList);
                resultList.add(dataObject);
            }

            return resultList;
        }

        private DataObject constructDataObject(ConcreteData concreteData, ObjectList objectList) {
            //table name
            String code = concreteData.getObjectCode();
            String tableName = concreteData.getTableName();
            String description = objectList.getObjectName(code);
            //should drop the table and create a new one?
            boolean rebuild = concreteData.isRebuild();
            //all columns' name
            ArrayList<String> allColumnsName = concreteData.getAllColumnsName();
            ArrayList<String> allColumnsTypeScript = concreteData.getAllColumnsTypeScript();
            //records
            ArrayList<HashMap<String, String>> records = concreteData.getRecords();

            Long time = concreteData.getTimestamp();

            //pending additional columns localstatus & localupdatetime
            //1.add columns
            allColumnsName.add(ADDITIONAL_STATUS);
            allColumnsName.add(ADDITIONAL_UPDATETIME);
            //2.add column type script
            allColumnsTypeScript.add(ADDITIONAL_STATUS_TYPE);//localstatus
            allColumnsTypeScript.add(ADDITIONAL_UPDATETIME_TYPE);//timestamp
            //3.fill the records
            for (HashMap hashMap : records) {
                hashMap.put(ADDITIONAL_STATUS, "1");
                //// TODO: 2017/4/1 type
                hashMap.put(ADDITIONAL_UPDATETIME, time +"");
            }

            DataObject dataObject = new DataObject(code, tableName, description, rebuild, allColumnsName,
                    allColumnsTypeScript, records);
            return dataObject;
        }
    }

}
