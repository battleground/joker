package com.abooc.joker.dialog;

import android.app.Activity;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.CountDownTimer;
import android.os.Vibrator;

/**
 * Description: <pre>摇一摇投屏事件</pre>
 * <p/>
 * Creator: 大宇
 * E-mail: allnet@live.cn
 * Date: 16/5/19
 */
public class SensorEventBuilder implements SensorEventListener {

    public interface EventListener {

        void onShake();
    }

    private SensorManager mSensorManager;
    private Vibrator mVibrator;
    private EventListener mEventListener;

    public SensorEventBuilder(Activity activity) {
        mSensorManager = (SensorManager) activity.getSystemService(Activity.SENSOR_SERVICE);
        mVibrator = (Vibrator) activity.getSystemService(Service.VIBRATOR_SERVICE);
    }

    public SensorEventBuilder builder(EventListener listener) {
        mEventListener = listener;
        return this;
    }

    public boolean enable() {
        return true;
    }

    public void turnOn() {
        if (mSensorManager != null) {
            mSensorManager.registerListener(this,
                    mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    public void turnOff() {
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private int iShakeNum;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER && !isBusy && enable()) {
            float[] values = sensorEvent.values;
            // Movement
            float x = values[0];
            float y = values[1];
            float z = values[2];

            float accelerationSquareRoot = (x * x + y * y + z * z)
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);

            int minValue = 10;
//            if ((Math.abs(x) > minValue || Math.abs(y) > minValue || Math.abs(z) > minValue)) {
            if (accelerationSquareRoot >= 4) {
                iShakeNum++;

                if (beShaking()) {
                    iShakeNum = 0;
                    isBusy = true;
                    mVibrator.vibrate(500);

                    timer.cancel();
                    timer.start();

                    if (mEventListener != null) {
                        mEventListener.onShake();
                    }
                }
            }
        }
    }

    private boolean beShaking() {
        return iShakeNum > 3;
    }

    private boolean isBusy = false;

    private CountDownTimer timer = new CountDownTimer(2000, 2000) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            isBusy = false;
        }
    };

}
