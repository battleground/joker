package com.abooc.joker.dialog.samples;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.abooc.joker.dialog.ScanningDialog;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.util.Debug;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dayu on 2017/3/5.
 */

public class AsyncScanningDialog extends ScanningDialog {

    public AsyncScanningDialog(Context context) {
        super(context);
    }

    @Override
    public void showListView() {
        if (!isEmpty()) {
            super.showListView();
        }
    }

    @Override
    public void onShowList(List<DeviceDisplay> list) {
        for (DeviceDisplay device : list) {
            deviceAdded(device);
        }
    }

    @Override
    public void deviceRemoved(DeviceDisplay device) {
        Debug.anchor(device.getDevice().getFriendlyName());
        super.deviceRemoved(device);
    }

    @Override
    public void deviceAdded(final DeviceDisplay device) {
        Debug.anchor(device.getDevice().getFriendlyName());
        TVPoster.post(device.getHost()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Debug.error(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = Message.obtain(mUIThread, 1, device);
                mUIThread.sendMessage(message);
            }
        });
    }

    private Handler mUIThread = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DeviceDisplay deviceDisplay = (DeviceDisplay) msg.obj;
            AsyncScanningDialog.super.deviceAdded(deviceDisplay);
        }
    };
}
