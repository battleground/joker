package com.abooc.joker.dialog.samples;

import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.abooc.util.Debug;

public class AActivity extends AppCompatActivity implements ServiceConnection {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        IntentService intentService = new IntentService("aa") {
            @Override
            protected void onHandleIntent(Intent intent) {

            }
        };

    }

    Service iService;

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        MyService.MyBinder myBinder = (MyService.MyBinder) binder;
        iService = myBinder.getService();

        Debug.anchor(iService.getClass().getSimpleName());
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Debug.error(name);

    }

    @Override
    protected void onDestroy() {
        Debug.anchor();
        super.onDestroy();
    }

    public void onBindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, this, Service.BIND_AUTO_CREATE);
    }

    public void onUnBindService(View view) {
        unbindService(this);
    }

    public void onStopSelfService(View view) {
        iService.stopSelf();
    }

    public void onStartService(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    public void onStopService(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}
