package com.thomas.frontend.persistent;

import android.content.Context;
import android.util.Log;

import com.example.queuelibrary.QueueObjectConverter;
import com.example.queuelibrary.SQLiteBusSubscriber;
import com.example.queuelibrary.SQLitePersistentQueue;
import com.google.gson.Gson;
import com.thomas.frontend.Payload;

/**
 * Created by thomasliao on 2017/3/29.
 */

public class SendQueue {

    private static final String TAG = SendQueue.class.getSimpleName();

    public SQLitePersistentQueue init(Context context) {
        Gson gson = new Gson();
        SQLitePersistentQueue queue = new SQLitePersistentQueue<>(context, new GsonPayloadConverter(gson));
        queue.getEventBus().subscribe(new Subscriber());
        return queue;
    }

    public class GsonPayloadConverter implements QueueObjectConverter<Payload> {
        private final Gson gson;

        public GsonPayloadConverter(Gson gson) {
            this.gson = gson;
        }

        @Override
        public Payload deserialize(String value) {
            Log.d(TAG, "converter deserialize " + value);
            return gson.fromJson(value, Payload.class);
        }

        @Override
        public String serialize(Payload queueObject) {
            Log.d(TAG, "serialize " + queueObject.toString());
            return gson.toJson(queueObject);
        }
    }

    public class Subscriber implements SQLiteBusSubscriber<Payload> {
        @Override
        public void onAdded(Payload object) {
            Log.d(TAG, object.toString() + " is added!");
        }

        @Override
        public void onRemoved(Payload object) {
            Log.d(TAG, object.toString() + " is removed!");
        }

        @Override
        public void onCleared() {

        }
    }

}
