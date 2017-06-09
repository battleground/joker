package com.abooc.joker.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.extra.Filter;
import com.abooc.upnp.model.DeviceDisplay;

/**
 * Created by dayu on 2017/2/14.
 */

public class ScannerSamples {
    public static String title;
    public static String error;
    public static ScanningDialog.OnSelectedDeviceListener onSelectedDeviceListener;

    private static boolean isShowing;

    public static boolean isShowing() {
        return isShowing;
    }

    public static void show(Context context) {
        show(context, null);
    }

    public static void show(Context context, Filter filter) {
        if (isShowing()) return;

        isShowing = true;
        final ScanningDialog iScanningDialog = new ScanningDialog(context);
        final UPnPScan iUPnPScan = new UPnPScan(iScanningDialog);
        iUPnPScan.setFilter(filter);
        iScanningDialog.setAlertTitle(title);
        iScanningDialog.setError(error);
        iScanningDialog.setOnSelectedDeviceListener(onSelectedDeviceListener);
        iScanningDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                iUPnPScan.scan();
            }
        });
        iScanningDialog.show();
        iScanningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                isShowing = false;
                iUPnPScan.quit();
                ScannerSamples.onSelectedDeviceListener = null;
            }
        });
    }

}

