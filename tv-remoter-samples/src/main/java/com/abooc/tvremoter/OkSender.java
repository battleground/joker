package com.abooc.tvremoter;

import android.support.annotation.NonNull;

import com.abooc.airremoter.Sender;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 16/7/27.
 */
public class OkSender implements Sender {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final int PORT_REMOTER = 21367;
    @Deprecated
    public static final int PORT_REMOTER_VR = 21368;

    //    String json = "{ \"code\":230, \"info\":\"\" }";
    private String mServer;

    private static OkSender mOur = new OkSender();
    private OkHttpClient httpClient = new OkHttpClient();

    private OkSender() {
    }

    public static OkSender create(String server, int port) {
        mOur.buildServer(server, port);
        return mOur;
    }

    private void buildServer(String server, int port) {
        mServer = "http://" + server + ":" + port;
    }

    @Override
    public void doSend(@NonNull String message) {
        out(mServer + "[POST:" + message + "]");

        RequestBody body = RequestBody.create(JSON, message);
        Request request = new Request.Builder()
                .url(mServer)
                .post(body)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                out(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                out(response.body().string());
            }

        });


    }

    static void out(String str) {
        System.out.println(str);
//                Debug.anchor(str);
    }
}
