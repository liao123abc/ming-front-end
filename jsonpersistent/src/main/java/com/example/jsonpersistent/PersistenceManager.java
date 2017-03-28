package com.example.jsonpersistent;

import android.content.Context;

/**
 * Created by thomasliao on 2017/3/23.
 */

public class PersistenceManager {
    private static volatile Persistent persistent;
    private static final Object logicalLock = new Object();

    public static Persistent getPersistenceManager(Context context, DataObjectConverter converter) {
        Persistent persistent = PersistenceManager.persistent;
        if (persistent == null) {
            synchronized (logicalLock) {//while waiting for lock, other thread might have init the object
                persistent = PersistenceManager.persistent;
                if ( persistent == null ) {//check again
                    persistent = new Persistent(context, converter);
                    PersistenceManager.persistent = persistent;
                }
            }
        }
        return persistent;
    }
}
