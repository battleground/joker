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

    private static DialogInterface.OnShowListener mOnShowListener;
    private static DialogInterface.OnDismissListener mOnDismissListener;

    public static void addOnShowListener(DialogInterface.OnShowListener showListener, DialogInterface.OnDismissListener dismissListener) {
        mOnShowListener = showListener;
        mOnDismissListener = dismissListener;
    }

    public static String title;
    public static String error;

    public static ScanningDialog show(Context context) {
        return show(context, null);
    }

    public static ScanningDialog show(Context context, Filter filter) {
        final ScanningDialog iScanningDialog = new ScanningDialog(context);
        final UPnPScan iUPnPScan = new UPnPScan(iScanningDialog);
        iUPnPScan.setFilter(filter);

        iScanningDialog.setMessage(title);
        iScanningDialog.setError(error);

        iScanningDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
//                iScanningDialog.setOnSelectedDeviceListener(registerEvent(iScanningDialog));
                iScanningDialog.setOnSelectedDeviceListener(onSelectedDeviceListener);

                if (mOnShowListener != null) mOnShowListener.onShow(dialog);
                iUPnPScan.start();
            }
        });

        iScanningDialog.show();

        iScanningDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                if (mOnDismissListener != null) mOnDismissListener.onDismiss(dialog);
                iUPnPScan.quit();

                onSelectedDeviceListener = null;
                mOnShowListener = null;
                mOnDismissListener = null;
            }
        });

        return iScanningDialog;
    }

    public static ScanningDialog.OnSelectedDeviceListener onSelectedDeviceListener;

    private static ScanningDialog.OnSelectedDeviceListener registerEvent(final ScanningDialog iScanningDialog) {
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
