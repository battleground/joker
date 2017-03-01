package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.abooc.airplay.Sender;


/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 16/7/27.
 */
public class Remoter implements Sender {

    public static final int PORT_REMOTER = 21367;
    @Deprecated
    public static final int PORT_REMOTER_VR = 21368;

    private String mDeviceIp = "192.168.1.156";
//    private AsyncHttpClient httpClient = new AsyncHttpClient();

    private Context mContext;

    private Remoter() {
    }

    public static Remoter create(Context context, String server, int port) {
        Remoter mOur = new Remoter();
        mOur.mContext = context;
        mOur.mDeviceIp = server;
        return mOur;
    }

//    private SimpleHttpHandler mSimpleHttpHandler = new SimpleHttpHandler();

    @Override
    public void doSend(@NonNull String message) {
//        try {
//            Debug.anchor(mDeviceIp + ":" + message);
//            HttpEntity entity = new StringEntity(message);
//            httpClient.post(mContext, "http://" + mDeviceIp + ":" + PORT_REMOTER, entity, "application/json", mSimpleHttpHandler);
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

    }

}
