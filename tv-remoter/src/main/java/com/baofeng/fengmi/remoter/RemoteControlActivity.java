package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.abooc.airremoter.Sender;
import com.abooc.joker.dialog.ScannerSamples;
import com.abooc.joker.dialog.ScanningDialog;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.widget.Toast;

/**
 * 遥控器
 */
public class RemoteControlActivity extends AppCompatActivity implements ScanningDialog.OnSelectedDeviceListener {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RemoteControlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    private TextView mTitleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_control);
        attachActionBar();

        // 新手指导
//        if (!Settings.getInstance().isGuideRemoteShown()) {
//            GuideRemoteActivity.launch(this);
//        }
        attachRemoter();

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        attachRemoter();
    }

    private void attachRemoter() {
        Sender sender = SenderImpl.getSender();
        KeyboardRemoter remoter = new KeyboardRemoter(sender);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_tv_control);
        RemoteControlFragment control = (RemoteControlFragment) fragment;
        control.setRemoter(remoter);
    }

    private void attachActionBar() {
        mTitleText = (TextView) findViewById(R.id.Title);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void onBackEvent(View view) {
        super.onBackPressed();
    }

    private ScanningDialog scanningDialog;

    public void onShowDevicesEvent(View view) {
        ScannerSamples.onSelectedDeviceListener = this;
        ScannerSamples.title = "选择要连接的设备";
        ScannerSamples.error = "未找到可用设备";
        scanningDialog = ScannerSamples.show(this, null);
    }

    @Override
    public void onSelectedDevice(DeviceDisplay deviceDisplay) {
        beRemoter(deviceDisplay);
    }

    private void beRemoter(DeviceDisplay display) {
        if (BaofengSupport.isBaofengTV(display.getOriginDevice())) {
            DlnaManager.getInstance().unbound();
            DlnaManager.getInstance().bind(display.getOriginDevice(), null);
            display.setChecked(true);


            String host = display.getHost();
            SenderImpl.buildSender(host);
            attachRemoter();
        } else {
            Toast.show("当前操作仅支持暴风TV");
        }
        scanningDialog.dismiss();
    }

    public void onSwitchEvent(View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_tv_control);
        RemoteControlFragment control = (RemoteControlFragment) fragment;
        control.doKeyboardSwitch();
    }

}
