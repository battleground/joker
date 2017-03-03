package com.bftv.tv_remoter_samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.widget.Toast;
import com.baofeng.fengmi.remoter.RemoteControlActivity;
import com.baofeng.fengmi.remoter.SenderImpl;

import org.fourthline.cling.android.AndroidUpnpServiceImpl;

public class SamplesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.init(this);

        setContentView(R.layout.activity_samples);


        Discovery.get().registerWiFiReceiver(this);
        DlnaManager.getInstance().startService(this, AndroidUpnpServiceImpl.class);
    }

    public void onGoto126Event(View view) {
        String ip = "192.168.1.126";
        OkSender sender = OkSender.create(ip, OkSender.PORT_REMOTER);
        SenderImpl.setSender(sender);
        SenderImpl.setBuilder(new OkBuilder());
        RemoteControlActivity.launch(this);
    }

    public void onGotoEvent(View view) {
        String ip = "0.0.0.0";
        OkSender sender = OkSender.create(ip, OkSender.PORT_REMOTER);
        SenderImpl.setSender(sender);
        SenderImpl.setBuilder(new OkBuilder());
        RemoteControlActivity.launch(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DlnaManager.getInstance().stop();
        Discovery.get().unregisterWiFiReceiver(this);
    }

}
