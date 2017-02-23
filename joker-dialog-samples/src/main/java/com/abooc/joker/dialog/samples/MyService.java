package com.abooc.joker.dialog.samples;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.abooc.util.Debug;

public class MyService extends Service {

    private MyBinder iBinder = new MyBinder();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Debug.anchor();
        return iBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        Debug.anchor();
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Debug.anchor();
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        Debug.anchor();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Debug.anchor();
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        Debug.anchor();
        super.onDestroy();
    }


    public class MyBinder extends Binder {
        public Service getService() {
            return MyService.this;
        }
    }

}
