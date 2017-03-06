package com.abooc.joker.dialog.samples;

import com.abooc.util.Debug;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by dayu on 2017/3/5.
 */

public class TVPoster {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();

    public static Call post(String ip) {
//        String url = "http://192.168.1.174:21367";
        String url = "http://" + ip + ":21367";
        String json = "{" +
                "\"code\":230," +
                "\"info\":\"\"" +
                "}";

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();


        return client.newCall(request);
//        .enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                String name = Thread.currentThread().getName();
//                Debug.error(name + "\n" + e.toString());
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String name = Thread.currentThread().getName();
//                Debug.anchor(name + "\n" + response.body().string());
//
//            }
//        });

    }

}
