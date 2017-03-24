package com.example.jsonpersistent;

import android.content.Context;

/**
 * Created by thomasliao on 2017/3/23.
 */

public class PersistenceManager {
    private static volatile JsonPersistent jsonPersistent;
    private static final Object logicalLock = new Object();

    private static JsonPersistent getPersistenceManager(Context context) {
        JsonPersistent persistent = jsonPersistent;
        if (persistent == null) {
            synchronized (logicalLock) {//while waiting for lock, other thread might have init the object
                persistent = jsonPersistent;
                if ( persistent == null ) {//check again
                    persistent = new JsonPersistent(context);
                    jsonPersistent = persistent;
                }
            }
        }
        return persistent;
    }

    public static boolean updateTables(Context context, String json) {
        JsonPersistent persistent = getPersistenceManager(context);
        persistent.updateTable(json);
        return false;
    }
}
