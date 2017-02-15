package com.abooc.joker.dialog.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abooc.joker.dialog.R;
import com.abooc.joker.dialog.ScannerSamples;
import com.abooc.joker.dialog.SensorEventBuilder;
import com.abooc.joker.dialog.ShakeDialog;
import com.abooc.joker.dialog.UPnP;
import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.RendererPlayer;
import com.abooc.upnp.model.DeviceDisplay;

import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.Photo;
import org.lee.java.util.Empty;

import java.util.ArrayList;

public class SamplesActivity extends AppCompatActivity implements SensorEventBuilder.EventListener {

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


    @Override
    public void onShake() {
        ArrayList<DeviceDisplay> list = Discovery.get().getList();
        if (Empty.isEmpty(list)) {
            final ShakeDialog iDialog = new ShakeDialog(this);
            iDialog.show();
//            onNoRouters();
        } else {

            if (list.size() == 1) {
//                saveVideo();
//                DeviceDisplay display = list.get(0);
//                DlnaManager.getInstance().bind(display.getOriginDevice(), null);
//                display.setChecked(true);
//                String json = getSeriesJson();
//                int seriesIndex = getSeriesIndex();
//                DLNAPlayerActivity.launch(this, json, seriesIndex);
            } else {
                if (DlnaManager.getInstance().hasBound()) {
//                    saveVideo();
//                    String json = getSeriesJson();
//                    int seriesIndex = getSeriesIndex();
//                    DLNAPlayerActivity.launch(this, json, seriesIndex);
                } else {
                    ScannerSamples.show(this);
                }
            }
        }
    }

}
