package com.abooc.joker.dialog;

import com.abooc.upnp.model.DeviceDisplay;

import java.util.List;

/**
 * Created by dayu on 2017/2/7.
 */

public interface ScanViewer {

    void onScanning();

    void onError();

    void onFinished();

    void showListView();

    void onShowList(List<DeviceDisplay> list);

    boolean isEmpty();

    boolean isScanning();

    void deviceAdded(DeviceDisplay device);

    void deviceRemoved(DeviceDisplay device);


}
