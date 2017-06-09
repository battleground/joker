package com.abooc.joker.dialog;

import android.os.Handler;
import android.os.Message;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.extra.Filter;
import com.abooc.upnp.model.CDevice;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.util.Debug;

import org.fourthline.cling.android.AndroidUpnpService;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

/**
 * Created by dayu on 2017/2/7.
 */

public class UPnPScan implements Scan {

    private ScanViewer iScanViewer;
    private Handler mUIThread = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (iScanViewer.isScanning()) {
                    iScanViewer.showListView();
                }
                iScanViewer.deviceAdded((DeviceDisplay) msg.obj);
            } else {
                iScanViewer.deviceRemoved((DeviceDisplay) msg.obj);

                if (!iUITimer.isRunning() && iScanViewer.isEmpty()) {
                    iScanViewer.onError();
                }
            }
        }
    };

    public UPnPScan(ScanViewer viewer) {
        iScanViewer = viewer;
        Discovery.get().addDefaultRegistryListener(iDefaultRegistryListener);
    }

    private DefaultRegistryListener iDefaultRegistryListener = new DefaultRegistryListener() {

        @Override
        public void deviceAdded(Registry registry, final Device device) {
            final DeviceDisplay deviceDisplay = checkingDevice(device);
            if (deviceDisplay == null) return;

            // 刷新列表
            Message message = Message.obtain(mUIThread, 1, deviceDisplay);
            mUIThread.sendMessage(message);
        }

        @Override
        public void deviceRemoved(Registry registry, final Device device) {
            Debug.anchor();
            DeviceDisplay deviceDisplay = new DeviceDisplay(new CDevice(device));
            // 刷新列表
            Message message = Message.obtain(mUIThread, -1, deviceDisplay);
//            mUIThread.sendMessage(message);
        }
    };

    private Filter iFilter = new Filter() {
        public boolean check(Device device) {
            return true;
        }
    };

    public void setFilter(Filter filter) {
        iFilter = filter;
    }

    private DeviceDisplay checkingDevice(Device device) {
        CDevice cDevice = new CDevice(device);
        DeviceDisplay deviceDisplay = new DeviceDisplay(cDevice);
        String host = getHost(device);
        deviceDisplay.setHost(host);
        return deviceDisplay;
    }

    private String getHost(Device device) {
        RemoteDeviceIdentity identity = (RemoteDeviceIdentity) device.getIdentity();
        String host = identity.getDescriptorURL().getHost();
        return host;
    }

    // TODO 测试
    private UITimer iUITimer = new UITimer(30) {
        @Override
        public void onStart() {
            AndroidUpnpService upnpService = DlnaManager.getInstance().getUpnpService();
            if (upnpService != null) {
                upnpService.getRegistry().removeAllRemoteDevices();
            }
            Discovery.get().searchAll();
            iScanViewer.onScanning();
        }

        @Override
        public void onTick(int total, int tick) {
            if (tick == 1) {
            }
        }

        @Override
        public void onCancel() {
            iScanViewer.onError();
        }

        @Override
        public void onFinish() {
            Debug.anchor();
            if (iScanViewer.isEmpty()) {
                iScanViewer.onError();
            } else {
                iScanViewer.onFinished();
            }
        }
    };

    @Override
    public void scan() {
        iUITimer.start();
    }

    @Override
    public void quit() {
        if (iUITimer.isRunning()) {
            iUITimer.cancel();
        }
        Discovery.get().addDefaultRegistryListener(null);
        iScanViewer = null;
    }
}

