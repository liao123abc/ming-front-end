package com.example.jsonpersistent;

import android.content.Context;

/**
 * Created by thomasliao on 2017/3/23.
 *
 * 对外公开的接口
 * 保持Persistent为单例
 *
 *
 * 这是一个增量更新手机端的解决方案：
 * 1. 手机端每次从服务器端获取数据（Json格式）， 直接使用该类库就可以持久化到本地
 * 2. 服务端返回的Json数据必须要包含有表明，表结构，表数据， 然后使用Converter来转换为该类库的DataObject
 * 3. 除了保存从服务器返回来的表，还会维护一个信息表，记录所有表的表名和列信息的HashCode，每次获取数据的时候，对比
 *    HashCode，判断是否表结构有变化。每当表有变更的时候，需要更新表信息。
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
