package com.abooc.joker.dialog;

import android.os.Handler;
import android.os.Message;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.extra.Filter;
import com.abooc.upnp.model.CDevice;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.util.Debug;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by dayu on 2017/2/7.
 */

public class UPnPScan implements Scan {

    private ScanViewer iScanViewer;
    private Handler mUIThread = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            DeviceDisplay deviceDisplay = (DeviceDisplay) msg.obj;
            if (msg.what == 1) {
                if (iScanViewer.isScanning()) {
                    iScanViewer.showListView();
                }
                iScanViewer.deviceAdded(deviceDisplay);
            } else {
                iScanViewer.deviceRemoved(deviceDisplay);

                if (iScanViewer.isEmpty()) {
                    iScanViewer.onError();
                }
            }
        }
    };

    public UPnPScan(ScanViewer viewer) {
        iScanViewer = viewer;
    }

    public void start() {
        final ArrayList<DeviceDisplay> list = Discovery.get().getList(iFilter);

        if (list == null || list.isEmpty()) {
//            iHandler.sendEmptyMessage(0);
            iUITimer.start();
        } else {
            iScanViewer.onScanning();

            Collections.sort(list, mComparatorByIP);
            iScanViewer.showListView();
            iScanViewer.onShowList(list);
            addListener();
        }
    }

    private void addListener() {
        Discovery.get().addDefaultRegistryListener(new DefaultRegistryListener() {

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
                DeviceDisplay deviceDisplay = new DeviceDisplay(new CDevice(device));
                // 刷新列表
                Message message = Message.obtain(mUIThread, -1, deviceDisplay);
                mUIThread.sendMessage(message);
            }
        });

    }

    Filter iFilter;
//    Filter iFilter = new Filter() {
//        @Override
//        public boolean check(Device device) {
//            return true;
//        }
//    };

    public void setFilter(Filter filter) {
        iFilter = filter;
    }

    private DeviceDisplay checkingDevice(Device device) {
        if (iFilter != null && !iFilter.check(device)) {
            return null;
        }

        CDevice cDevice = new CDevice(device);
        DeviceDisplay deviceDisplay = new DeviceDisplay(cDevice);
        String host = getHost(device);
        deviceDisplay.setHost(host);

        if (DlnaManager.getInstance().isBound(device)) {
            deviceDisplay.setChecked(true);
        }
        return deviceDisplay;
    }

    private String getHost(Device device) {
        RemoteDeviceIdentity identity = (RemoteDeviceIdentity) device.getIdentity();
        String host = identity.getDescriptorURL().getHost();
        return host;
    }


    // TODO 测试
    private final Handler iHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Debug.anchor("" + msg.what);

            switch (msg.what) {
                case 0:
                    iScanViewer.onScanning();
                    sendEmptyMessageDelayed(1, 3000);
                    break;
                case 1:
                    addListener();
                    break;
                case -1:
                    iScanViewer.onError();
                    break;
            }
        }
    };


    private UITimer iUITimer = new UITimer(3) {
        @Override
        public void onStart() {
            iScanViewer.onScanning();

            Discovery.get().removeAll();
        }

        @Override
        public void onTick(int total, int tick) {
            if (tick == 1) {
                addListener();
                Discovery.get().searchAll();
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
                iScanViewer.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void scan() {
        Discovery.get().search();
        iScanViewer.onScanning();
    }


    /**
     * 根据IP地址排序
     */
    static Comparator<DeviceDisplay> mComparatorByIP = new Comparator<DeviceDisplay>() {
        @Override
        public int compare(DeviceDisplay device, DeviceDisplay another) {

            int ipThis = getIP(device.getHost());
            int ipAnother = getIP(another.getHost());

            if (ipThis > ipAnother) {
                return 1;
            } else if (ipThis < ipAnother) {
                return -1;
            } else {
                return 0;
            }
        }
    };

    static int getIP(String host) {
        String ipThis = host.substring(host.lastIndexOf(".") + 1);
        return Integer.valueOf(ipThis).intValue();
    }

    @Override
    public void quit() {
        iHandler.removeMessages(-1);
        iHandler.removeMessages(0);
        iHandler.removeMessages(1);
        Discovery.get().addDefaultRegistryListener(null);
    }
}
