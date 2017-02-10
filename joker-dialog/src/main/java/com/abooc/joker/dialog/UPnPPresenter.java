package com.abooc.joker.dialog;

import android.os.Handler;
import android.os.Message;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.model.CDevice;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.util.Debug;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.RemoteDeviceIdentity;
import org.fourthline.cling.registry.DefaultRegistryListener;
import org.fourthline.cling.registry.Registry;

import java.util.ArrayList;
import java.util.Comparator;

import static java.util.Collections.sort;

/**
 * Created by dayu on 2017/2/7.
 */

public class UPnPPresenter implements Presenter {

    Viewer mViewer;
    Handler mHandler;

    public UPnPPresenter(Viewer viewer, Handler handler) {
        mViewer = viewer;
        mHandler = handler;

    }

    public void start() {
        final ArrayList<DeviceDisplay> list = Discovery.get().getList();

        if (list == null || list.isEmpty()) {
//            iHandler.sendEmptyMessage(0);
            iUITimer.start();
        } else {
            mViewer.onScanning();

            sort(list, mComparatorByIP);
            mViewer.showListView();
            mViewer.onShowList(list);
            addListener();
        }
    }

    void addListener() {
        Discovery.get().addDefaultRegistryListener(new DefaultRegistryListener() {

            @Override
            public void deviceAdded(Registry registry, final Device device) {
                final DeviceDisplay deviceDisplay = checkingDevice(device);
                if (deviceDisplay == null) return;

                // 刷新列表
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mViewer.isScanning()) {
                            mViewer.showListView();
                        }
                        mViewer.deviceAdded(deviceDisplay);
                    }
                });

            }

            @Override
            public void deviceRemoved(Registry registry, final Device device) {
                // 刷新列表
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        DeviceDisplay deviceDisplay = new DeviceDisplay(new CDevice(device));
                        mViewer.deviceRemoved(deviceDisplay);

                        if (mViewer.isEmpty()) {
                            mViewer.onError();
                        }
                    }
                });
            }
        });

    }

    DeviceDisplay checkingDevice(Device device) {
        CDevice cDevice = new CDevice(device);
        if (cDevice.asService("AVTransport")) {
            DeviceDisplay deviceDisplay = new DeviceDisplay(cDevice);
            String host = getHost(device);
            deviceDisplay.setHost(host);

            DeviceIdentity boundIdentity = DlnaManager.getInstance().getBoundIdentity();
            if (device.getIdentity().equals(boundIdentity)) {
                deviceDisplay.setChecked(true);
            }
            return deviceDisplay;
        }

        return null;
    }

    String getHost(Device device) {
        RemoteDeviceIdentity identity = (RemoteDeviceIdentity) device.getIdentity();
        String host = identity.getDescriptorURL().getHost();
        return host;
    }


    final Handler iHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Debug.anchor("" + msg.what);

            switch (msg.what) {
                case 0:
                    mViewer.onScanning();
                    sendEmptyMessageDelayed(1, 3000);
                    break;
                case 1:
                    addListener();
                    break;
                case -1:
                    mViewer.onError();
                    break;
            }
        }
    };


    private UITimer iUITimer = new UITimer(3) {
        @Override
        public void onStart() {
            mViewer.onScanning();

            Discovery.get().removeAll();
//            DlnaManager.getInstance().getUpnpService().getControlPoint().search();
        }

        @Override
        public void onTick(int total, int tick) {
            if(tick == 1){
                addListener();
            }
        }

        @Override
        public void onCancel() {
            mViewer.onError();
        }

        @Override
        public void onFinish() {
            Debug.anchor();
            if (mViewer.isEmpty()) {
                mViewer.onError();
            } else {
                mViewer.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void scan() {
        Discovery.get().search();
        mViewer.onScanning();
    }


    /**
     * 根据IP地址排序
     */
    private Comparator<DeviceDisplay> mComparatorByIP = new Comparator<DeviceDisplay>() {
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

    int getIP(String host) {
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
