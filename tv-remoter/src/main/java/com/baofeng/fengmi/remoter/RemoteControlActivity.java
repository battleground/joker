package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.abooc.airplay.Sender;
//import com.abooc.upnp.DlnaManager;

/**
 * 遥控器
 */
public class RemoteControlActivity extends AppCompatActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, RemoteControlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void launch(Context context, boolean isVr) {
        Intent intent = new Intent(context, RemoteControlActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("isVr", isVr);
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
//        String ip = DlnaManager.getInstance().getBoundIp();
        String ip = "";
        Sender sender = Remoter.create(this, ip, Remoter.PORT_REMOTER);
        KeyboardRemoter remoter = new KeyboardRemoter(sender);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_tv_control);
        RemoteControlFragment control = (RemoteControlFragment) fragment;
        control.setRemoter(remoter);
    }

    private void attachActionBar() {
        mTitleText = (TextView) findViewById(R.id.Title);
        findViewById(R.id.VR).setVisibility(View.GONE);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    public void onBackEvent(View view) {
        super.onBackPressed();
    }

    public void onShowDevicesEvent(View view) {
    }

    public void onSwitchEvent(View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_tv_control);
        RemoteControlFragment control = (RemoteControlFragment) fragment;
        control.doKeyboardSwitch();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
