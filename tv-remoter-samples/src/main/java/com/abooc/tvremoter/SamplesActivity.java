package com.abooc.tvremoter;

import android.os.Bundle;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.widget.Toast;
import com.baofeng.fengmi.remoter.RemoteControlActivity;
import com.baofeng.fengmi.remoter.SenderImpl;

import org.fourthline.cling.android.AndroidUpnpServiceImpl;

public class SamplesActivity extends RemoteControlActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.init(this);
        DlnaManager.getInstance().setLoggerEnable(true);
        Discovery.get().registerWiFiReceiver(this);
        DlnaManager.getInstance().startService(this, AndroidUpnpServiceImpl.class);
        SenderImpl.setBuilder(new OkBuilder());

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DlnaManager.getInstance().stop();
        Discovery.get().unregisterWiFiReceiver(this);
    }

}
