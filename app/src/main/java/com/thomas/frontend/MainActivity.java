package com.thomas.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jsonpersistent.PersistenceManager;
import com.example.queuelibrary.QueueObjectConverter;
import com.example.queuelibrary.SQLiteBusSubscriber;
import com.example.queuelibrary.SQLitePersistentQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.internal.LinkedTreeMap;
import com.thomas.frontend.model.data.TableData;
import com.thomas.frontend.model.info.ObjectList;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        button = (Button) findViewById(R.id.button);

        Gson gson = new Gson();
        final SQLitePersistentQueue queue = new SQLitePersistentQueue<>(this, new GsonPayloadConverter(gson));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToQueue(queue);
            }
        });

    }

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = getAssets().open("offline/" + fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void addToQueue(SQLitePersistentQueue queue) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", "123");
        jsonObject.addProperty("name", "thomas");
        jsonObject.addProperty("phone", "1423442938948");

        Payload payload = new Payload("1", "2", "3");
        queue.add(payload);

        String info = "info.json";
        String sample = "sample.json";

        String information = loadJSONFromAsset(info);
        String content = loadJSONFromAsset(sample);

        PersistentSample persistentSample = new PersistentSample();
        persistentSample.test(this, information, content);

//        String json = "{\"protocols\": {\"header\": \"终端数统计-饼图\",\"footer\": null,\"event\": null,\"tooltip\": null,\"label\": null,\"fieldname\": \"salesareaname\",\"field\": \"storecode\",\"widgettype\": \"pie\"},\"datas\": [{\"salesareaname\": \"华北战区\",\"storecode\": \"2\"},{\"salesareaname\": \"华中战区\",\"storecode\": \"4\"},{\"salesareaname\": \"华南战区\",\"storecode\": \"7\"}]} ";
//        Object gson = new GsonBuilder().create().fromJson(json, Object.class);
//        Log.d(TAG, gson.toString());
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

    public class Subscribe implements SQLiteBusSubscriber<Payload> {
        @Override
        public void onAdded(Payload object) {

        }

        @Override
        public void onRemoved(Payload object) {

        }

        @Override
        public void onCleared() {

        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
