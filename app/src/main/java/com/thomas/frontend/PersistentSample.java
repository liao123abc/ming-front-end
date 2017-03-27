package com.thomas.frontend;

import android.util.Log;

import com.example.jsonpersistent.DataObjectConverter;
import com.example.jsonpersistent.model.DataObject;
import com.google.gson.GsonBuilder;
import com.thomas.frontend.model.info.ObjectList;

/**
 * Created by thomasliao on 2017/3/25.
 */

public class PersistentSample {
    private static final String TAG = PersistentSample.class.getSimpleName();


    private void test(String json) {
        if (json != null && !json.equals("")) {
            ObjectList objectList = new GsonBuilder().create().fromJson(json, ObjectList.class);
            Log.d(TAG, objectList.toString());
        }
    }

    class MyDataObjectConverter implements DataObjectConverter<> {

    }

}
