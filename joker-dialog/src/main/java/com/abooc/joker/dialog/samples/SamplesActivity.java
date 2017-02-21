package com.abooc.joker.dialog.samples;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.abooc.joker.dialog.R;
import com.abooc.joker.dialog.ScannerSamples;
import com.abooc.joker.dialog.ScanningDialog;
import com.abooc.joker.dialog.SensorEventBuilder;
import com.abooc.joker.dialog.ShakeDialog;
import com.abooc.joker.dialog.UPnP;
import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.RendererPlayer;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.util.Debug;

import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.Photo;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SamplesActivity extends Activity implements SensorEventBuilder.EventListener,
        DialogInterface.OnShowListener, DialogInterface.OnDismissListener, ScanningDialog.OnSelectedDeviceListener {

    private SensorEventBuilder mSensorBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorBuilder = new SensorEventBuilder(this).builder(this);

        Discovery.get().init(SamplesActivity.this);
        DlnaManager.getInstance().startService(SamplesActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorBuilder.turnOn();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorBuilder.turnOff();
    }

    public void onShowScanningDialog(View view) {
        ScannerSamples.onSelectedDeviceListener = this;
        ScannerSamples.addOnShowListener(this, this);
        ScannerSamples.show(this);
    }

    public void onFlyImage(View view) {
        flyImage(null);
    }

    public void flyImage(String url) {
        url = "http://images.apple.com/v/home/cx/images/gallery/iphone_square_large.jpg";
        Res res = UPnP.buildRes("image/jpeg", "filePath", url, 0);
        Photo videoItem = new Photo("1", String.valueOf(1), "The New iPhone7", "unknown", "unknown", res);
        String metadata = UPnP.buildMetadataXml(videoItem);

        RendererPlayer player = RendererPlayer.get();
        player.start(url, metadata);
    }

    public void onFlyTVVideo(View view) {
        String url = "http://images.apple.com/v/home/cx/images/gallery/iphone_square_large.jpg";
        Res res = UPnP.buildRes("image/jpeg", "filePath", url, 0);

//        TVVideoItem item = new TVVideoItem("1", "1", "标题", "unknown", res);
//        item.setChannelID("222");
//        item.setChannelUid("1");
//        item.setChannelName("张三的频道");
//        item.setChannelNickname("张三");
//        item.setChannelAvatar("http://avatar-img.b0.upaiyun.com//imgface//origin//2016-03//3b67a03f2901694a6f7fa48a74d349d9.jpg!180x180");
//        String metadata = UPnP.buildMetadataXml(item);

//        RendererPlayer player = RendererPlayer.get();
//        player.start(url, metadata);
    }


    @Override
    public void onShake() {
        if (!ShakeDialog.hasShown) {
            final ShakeDialog iDialog = new ShakeDialog(this);
            iDialog.show();
            return;
        }

        ArrayList<DeviceDisplay> list = Discovery.get().getList();
        if (list.size() == 1) {
//                saveVideo();
//                DeviceDisplay display = list.get(0);
//                DlnaManager.getInstance().bind(display.getOriginDevice(), null);
//                display.setChecked(true);
//                String json = getSeriesJson();
//                int seriesIndex = getSeriesIndex();
//                DLNAPlayerActivity.launch(this, json, seriesIndex);
        } else {
            ScannerSamples.onSelectedDeviceListener = this;
            ScannerSamples.addOnShowListener(this, this);
            ScannerSamples.show(this);
        }
    }

    @Override
    public void onSelectedDevice(DeviceDisplay device) {

        post(device.getHost());
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public void post(String ip) {
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


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String name = Thread.currentThread().getName();
                Debug.error(name + "\n" + e.toString());

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String name = Thread.currentThread().getName();
                Debug.anchor(name + "\n" + response.body().string());

            }
        });

    }

    @Override
    public void onShow(DialogInterface dialog) {
        mSensorBuilder.turnOff();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        mSensorBuilder.turnOn();
    }

}
