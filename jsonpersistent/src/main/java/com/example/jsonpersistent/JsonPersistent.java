package com.example.jsonpersistent;

import android.content.Context;
import android.util.Log;

import com.example.jsonpersistent.db.SQLiteTableManager;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * 将json保存到sqlite中
 *
 */
public class JsonPersistent {

    private static final String TAG = JsonPersistent.class.getSimpleName();

    private SQLiteTableManager sqliteTableManager;
    private Context context;

    public JsonPersistent(Context context) {
        this.context = context;
    }

    /**
     * update table according to the json
     * @param json
     * @return
     */
    public boolean updateTable(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject != null) {
            Log.d(TAG, jsonObject.toString());
        }
        return false;
    }

    /**
     * parse JsonObject to Flat data , and save to db by property
     * @param json
     * @return
     */
    public boolean persistent(String json) {

        return false;
    }

}
