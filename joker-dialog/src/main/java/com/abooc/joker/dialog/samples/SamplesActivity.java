package com.abooc.joker.dialog.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abooc.joker.dialog.R;
import com.abooc.joker.dialog.ScannerSamples;
import com.abooc.joker.dialog.UPnP;
import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.RendererPlayer;

import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.Photo;

public class SamplesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Discovery.get().init(SamplesActivity.this);
        DlnaManager.getInstance().startService(SamplesActivity.this);
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

}
