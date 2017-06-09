package com.abooc.joker.dialog;

import android.text.TextUtils;

import com.abooc.upnp.model.DeviceDisplay;

import java.util.Comparator;

public class IPComparator implements Comparator<DeviceDisplay> {

    @Override
    public int compare(DeviceDisplay lhs, DeviceDisplay rhs) {
        String host = lhs.getHost();
        String anotherHost = rhs.getHost();

        if (TextUtils.isEmpty(host) || TextUtils.isEmpty(anotherHost)) {
            return 0;
        }

        String ipThis = host.substring(host.lastIndexOf(".") + 1);
        Integer intThis = Integer.valueOf(ipThis);

        String ipAnother = anotherHost.substring(anotherHost.lastIndexOf(".") + 1);
        Integer intAnother = Integer.valueOf(ipAnother);

        if (intThis > intAnother) {
            return 1;
        } else if (intThis < intAnother) {
            return -1;
        } else {
            return 0;
        }
    }
}