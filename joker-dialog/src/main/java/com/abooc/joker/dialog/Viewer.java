package com.abooc.joker.dialog;

import com.abooc.upnp.model.DeviceDisplay;

import java.util.List;

/**
 * Created by dayu on 2017/2/7.
 */

public interface Viewer {

    void onScanning();

    void onError();

    void notifyDataSetChanged();

    void showListView();

    void onShowList(List<DeviceDisplay> list);

    boolean isEmpty();

    boolean isScanning();

    void deviceAdded(DeviceDisplay device);

    void deviceRemoved(DeviceDisplay device);


}
