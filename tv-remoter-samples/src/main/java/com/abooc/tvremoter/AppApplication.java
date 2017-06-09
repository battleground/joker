package com.abooc.tvremoter;


import android.app.Application;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.widget.Toast;
import com.baofeng.fengmi.remoter.SenderImpl;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import org.fourthline.cling.android.AndroidUpnpServiceImpl;

/**
 * Created by dayu on 2017/6/9.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 语音转文字初始化
         */
        String params = SpeechConstant.APPID + "=" + getString(R.string.app_id)
                + "," + SpeechConstant.FORCE_LOGIN + "=true";
        SpeechUtility.createUtility(this, params);

        Toast.init(this);
        DlnaManager.getInstance().setLoggerEnable(true);
        Discovery.get().registerWiFiReceiver(this);
        DlnaManager.getInstance().startService(this, AndroidUpnpServiceImpl.class);
        SenderImpl.setBuilder(new OkBuilder());
    }
}
