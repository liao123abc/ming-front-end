package com.thomas.frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.queuelibrary.QueueObjectConverter;
import com.example.queuelibrary.SQLiteBusSubscriber;
import com.example.queuelibrary.SQLitePersistentQueue;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.thomas.frontend.persistent.PersistentSample;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getSimpleName();
    private Button button;

    private SQLitePersistentQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        button = (Button) findViewById(R.id.button);


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
        try {
            queue.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testPersistentOffline() {
        String info = "info.json";
        String sample = "sample.json";

        String information = loadJSONFromAsset(info);
        String content = loadJSONFromAsset(sample);
        PersistentSample persistentSample = new PersistentSample();
        persistentSample.test(this, information, content);
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
