package com.abooc.tvremoter;

import android.os.Bundle;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.baofeng.fengmi.remoter.RemoteControlActivity;

public class SamplesActivity extends RemoteControlActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DlnaManager.getInstance().stop();
        Discovery.get().unregisterWiFiReceiver(this);
    }

}
