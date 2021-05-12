package com.example.androidnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidnetwork.domain.GetTextItem;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void loadJson(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://10.0.2.2:9102/get/text");
                    HttpURLConnection connection= ((HttpURLConnection) url.openConnection());
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    //设置请求头
                    connection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9");
                    connection.setRequestProperty("Accept","*/*");
                    //网络连接
                    connection.connect();
                    //结果码
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200){
                        Map<String, List<String>> headerFields = connection.getHeaderFields();
                        //遍历map集合，拿到头部信息
                        Set<Map.Entry<String , List<String>>> entries = headerFields.entrySet();
                        for (Map.Entry<String,List<String>> entry : entries){
                            Log.d(TAG,entry.getKey() + "==" + entry.getValue());
                        }

                        InputStream inputStream = connection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        String json = bufferedReader.readLine();
                        Log.d(TAG, "line-->" + json);
                        Gson gson = new Gson();
                        GetTextItem getTextItem = gson.fromJson(json,GetTextItem.class);

                        updateUI(getTextItem);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void updateUI(GetTextItem getTextItem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}