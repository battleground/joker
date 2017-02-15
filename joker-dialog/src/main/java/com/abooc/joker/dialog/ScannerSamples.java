package com.abooc.joker.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.model.DeviceDisplay;

/**
 * Created by dayu on 2017/2/14.
 */

public class ScannerSamples extends UPnPPresenter {

    public ScannerSamples(Viewer viewer, Handler handler) {
        super(viewer, handler);
    }

    public static void show(Context context) {
        final ScanningDialog iScanningDialog = new ScanningDialog(context);
        final UPnPPresenter iUPnPPresenter = new UPnPPresenter(iScanningDialog, new Handler());
        iScanningDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                iScanningDialog.setOnSelectedDeviceListener(registerEvent(iScanningDialog));

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

    public static ScanningDialog.OnSelectedDeviceListener onSelectedDeviceListener;

    static ScanningDialog.OnSelectedDeviceListener registerEvent(final ScanningDialog iScanningDialog) {
        return new ScanningDialog.OnSelectedDeviceListener() {
            @Override
            public void onSelectedDevice(final DeviceDisplay device) {

                boolean bind = DlnaManager.getInstance().bind(device.getOriginDevice(), null);
                if (bind) {
                    device.setChecked(true);

                    iScanningDialog.notifyDataSetChanged();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iScanningDialog.dismiss();
                        if (onSelectedDeviceListener != null) {
                            onSelectedDeviceListener.onSelectedDevice(device);
                        }
                    }
                }, 300);
            }
        };
    }

}
