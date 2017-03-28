package com.thomas.frontend;

import android.app.ActionBar;
import android.content.Context;
import android.util.Log;

import com.example.jsonpersistent.DataObjectConverter;
import com.example.jsonpersistent.PersistenceManager;
import com.example.jsonpersistent.Persistent;
import com.example.jsonpersistent.model.DataObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thomas.frontend.model.data.TableData;
import com.thomas.frontend.model.info.ObjectList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomasliao on 2017/3/25.
 */

public class PersistentSample {
    private static final String TAG = PersistentSample.class.getSimpleName();

    public void test(Context context, String info, String content) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("info", info);
        hashMap.put("content", content);
        GsonBuilder builder = new GsonBuilder();
        Gson o = builder.create();

        MyDataObjectConverter converter = new MyDataObjectConverter(o);
        Persistent persistent = PersistenceManager.getPersistenceManager(context, converter);

    }


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
            //
            String infoJson = hashMap.get("info");
            String contentJson = hashMap.get("content");
            if (infoJson != null && !infoJson.equals("")
                    && contentJson != null && !contentJson.equals("")
                    ) {
                TableData tableData = gson.fromJson(contentJson, TableData.class);
                ObjectList objectList = gson.fromJson(infoJson, ObjectList.class);//对象列表
            }
            //// TODO: 2017/3/27  generate list of dataobjcet
            return null;
        }

        private List<DataObject> constructDataList(TableData tableData, ObjectList objectList) {
            return null;
        }
    }

}
