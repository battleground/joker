package com.abooc.joker.dialog.samples;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abooc.joker.dialog.R;
import com.abooc.joker.dialog.ScanningDialog;
import com.abooc.joker.dialog.UPnP;
import com.abooc.joker.dialog.UPnPPresenter;
import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.RendererPlayer;
import com.abooc.upnp.model.DeviceDisplay;

import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.item.Photo;

public class SamplesActivity extends AppCompatActivity implements ScanningDialog.OnSelectedDeviceListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Discovery.get().init(SamplesActivity.this);
        DlnaManager.getInstance().startService(SamplesActivity.this);
    }

    @Override
    public void onSelectedDevice(DeviceDisplay device) {

        boolean bind = DlnaManager.getInstance().bind(device.getOriginDevice(), null);
        if (bind) {
            device.setChecked(true);

            iScanningDialog.notifyDataSetChanged();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                iScanningDialog.dismiss();
            }
        }, 300);
    }

    public void onFlyImage(View view) {
        flyImage(null);
    }

    public void flyImage(String url) {
        url = "http://images.apple.com/v/home/cx/images/gallery/iphone_square_large.jpg";
        Res res = UPnP.buildRes("image/jpeg", "filePath", url, 0);
        Photo videoItem = new Photo("1", String.valueOf(1), "图来了", "unknown", "unknown", res);
        String metadata = UPnP.buildMetadataXml(videoItem);

        RendererPlayer player = RendererPlayer.get();
        player.start(url, metadata);
    }

    UPnPPresenter iUPnPPresenter;
    ScanningDialog iScanningDialog;

    public void onShowScanningDialog(View view) {
        iScanningDialog = new ScanningDialog(this);
        iScanningDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                iScanningDialog.setOnSelectedDeviceListener(SamplesActivity.this);
                iUPnPPresenter = new UPnPPresenter(iScanningDialog, new Handler());

                iUPnPPresenter.start();
            }
        });

        iScanningDialog.show();

        iScanningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                iUPnPPresenter.quit();
            }
        });

    }

}
