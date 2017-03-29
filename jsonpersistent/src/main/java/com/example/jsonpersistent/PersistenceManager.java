package com.example.jsonpersistent;

import android.content.Context;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * 对外公开的接口
 *
 *
 */

public class PersistenceManager {
    private static volatile Persistent persistent;
    private static final Object logicalLock = new Object();

    public static Persistent openPersistent(Context context, DataObjectConverter converter) {
        Persistent persistent = PersistenceManager.persistent;
        if (persistent == null) {
            synchronized (logicalLock) {//while waiting for lock, other thread might have init the object
                persistent = PersistenceManager.persistent;
                if ( persistent == null ) {//check again
                    persistent = new Persistent(context, converter);//create and open db
                    PersistenceManager.persistent = persistent;
                }
            }
        } else {
            persistent.open();//open the db
        }
        return persistent;
    }
}
