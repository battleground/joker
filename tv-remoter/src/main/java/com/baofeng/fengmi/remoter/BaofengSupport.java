package com.baofeng.fengmi.remoter;

import com.abooc.util.Debug;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 16/8/8.
 */
public class BaofengSupport {

    static String BAOFENG_TV = "baofengtv";
    static String BAOFENG_VR = "baofengtv:vr";

    public static boolean isBaofengTV(Device device) {
        String manufacturer = device.getDetails().getManufacturerDetails().getManufacturer();
        Debug.anchor(manufacturer);
        return BAOFENG_TV.equalsIgnoreCase(manufacturer) || BAOFENG_VR.equalsIgnoreCase(manufacturer);
    }

    public static boolean isSupportVR(Device device) {
        String manufacturer = device.getDetails().getManufacturerDetails().getManufacturer();
        return BAOFENG_VR.equalsIgnoreCase(manufacturer);
    }

}