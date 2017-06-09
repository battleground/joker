package com.baofeng.fengmi.remoter;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.abooc.util.Debug;
import com.baofeng.fengmi.lib.voice.dialog.AudioRecorderButton;
import com.baofeng.fengmi.remoter.KeyboardRemoter.KeyCode;

/**
 * 遥控器
 *
 * @author zhangjunpu
 * @date 16/4/21
 */
public class RemoteControlFragment extends Fragment implements
        View.OnLongClickListener, View.OnTouchListener {

    private View mPower; //电源
    private View mHome; //主页
    private View mBack; //返回
    private View mMenu; //菜单
    private AudioRecorderButton mBiu; //Biu键
    private View mVolumeUp; //音量加
    private View mVolumeDown; //音量键

    private KeyboardView mKeyboardView;
    private TouchSweepView mTouchSweepEventView;

    private KeyboardRemoter mRemoter;
    private SoundPool mSoundPool;
    private int mSoundID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_control, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mKeyboardView = (KeyboardView) view.findViewById(R.id.Keyboard);
        mTouchSweepEventView = (TouchSweepView) view.findViewById(R.id.view_touch);

        mKeyboardView.setOnItemKeyLongClickListener(this);
        mKeyboardView.setOnItemKeyTouchListener(this);
        mTouchSweepEventView.setOnSweepEventListener(new TouchSweepView.OnSweepEventListener() {
            @Override
            public void onSweepEvent(int keyCode, int action) {
                mRemoter.send(keyCode, MotionEvent.ACTION_DOWN);
                mRemoter.send(keyCode, MotionEvent.ACTION_UP);
            }
        });

        init(view);

//        mSoundPool = new SoundPool.Builder().build();
        mSoundPool = new SoundPool(5, AudioManager.STREAM_RING, 0);
        mSoundID = mSoundPool.load(getContext().getApplicationContext(), R.raw.fm_controller_button_pressed, 1);
    }

    private void init(View view) {
        mPower = view.findViewById(R.id.power);
        mHome = view.findViewById(R.id.home);
        mBack = view.findViewById(R.id.back);
        mMenu = view.findViewById(R.id.menu);
        mBiu = (AudioRecorderButton) view.findViewById(R.id.biu);
        mVolumeUp = view.findViewById(R.id.volume_up);
        mVolumeDown = view.findViewById(R.id.volume_down);


        mPower.setOnTouchListener(this);
        mHome.setOnTouchListener(this);
        mBack.setOnTouchListener(this);
        mMenu.setOnTouchListener(this);
        mVolumeUp.setOnTouchListener(this);
        mVolumeDown.setOnTouchListener(this);


        mPower.setOnLongClickListener(this);
        mHome.setOnLongClickListener(this);
        mBack.setOnLongClickListener(this);
        mMenu.setOnLongClickListener(this);
        mVolumeUp.setOnLongClickListener(this);
        mVolumeDown.setOnLongClickListener(this);

        mBiu.setOnTouchListener(getOnBiuTouchListener());
        mBiu.setOnClickListener(getOnBiuClickListener());
        mBiu.setOnLongClickListener(this);
        mBiu.setOnAudioFinishRecordListener(new AudioRecorderButton.OnAudioFinishRecordListener() {
            @Override
            public void onFinish(String result) {
                if (result != null && result.length() > 0) {
                    mRemoter.sendVoice(KeyCode.BIU, result);
                    Debug.anchor("语音输出结果:" + result);
                }
            }
        });

    }

    public void setRemoter(KeyboardRemoter remoter) {
        mRemoter = remoter;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void doKeyboardSwitch() {
        if (mTouchSweepEventView.getVisibility() == View.VISIBLE) {
            showKeyboardView();
        } else {
            showSweepEventView();
        }
    }

    private void showKeyboardView() {
        mKeyboardView.setVisibility(View.VISIBLE);
        mTouchSweepEventView.setVisibility(View.GONE);
    }

    private void showSweepEventView() {
        mTouchSweepEventView.setVisibility(View.VISIBLE);
        mKeyboardView.setVisibility(View.GONE);
    }

    private int getKeyCodeByView(View v) {
        int i = v.getId();
        if (i == R.id.power) {
            return KeyCode.POWER;
        } else if (i == R.id.home) {
            return KeyCode.HOME;
        } else if (i == R.id.back) {
            return KeyCode.BACK;
        } else if (i == R.id.menu) {
            return KeyCode.MENU;
        } else if (i == R.id.biu) {
            return KeyCode.BIU;
        } else if (i == R.id.volume_up) {
            return KeyCode.VOLUME_UP;
        } else if (i == R.id.volume_down) {
            return KeyCode.VOLUME_DOWN;
        } else if (i == R.id.up) {
            return KeyCode.UP;
        } else if (i == R.id.down) {
            return KeyCode.DOWN;
        } else if (i == R.id.left) {
            return KeyCode.LEFT;
        } else if (i == R.id.right) {
            return KeyCode.RIGHT;
        } else if (i == R.id.submit) {
            return KeyCode.OK;
        }
        return 0;
    }

    private void playSound() {
        mSoundPool.play(
                mSoundID,
                1.0f,      //左耳道音量【0~1】
                1.0f,      //右耳道音量【0~1】
                0,         //播放优先级【0表示最低优先级】
                1,         //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1          //播放速度【1是正常，范围从0~2】
        );
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                longPressing = false;
                playSound();

                int keyCode = getKeyCodeByView(v);
                mRemoter.send(keyCode, MotionEvent.ACTION_DOWN);
                break;
            }
            case MotionEvent.ACTION_UP: {
                longPressing = false;
                int keyCode = getKeyCodeByView(v);
                mRemoter.send(keyCode, MotionEvent.ACTION_UP);
                break;
            }
        }
        return false;
    }

    private boolean longPressing = false;

    private Handler GoGo = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (longPressing) {
                mRemoter.send(msg.what, MotionEvent.ACTION_MOVE);
                GoGo.sendEmptyMessageDelayed(msg.what, 100);
            }
        }

    };

    @Override
    public boolean onLongClick(View v) {
        longPressing = true;
        if (v.getId() == R.id.biu) {
            onBiuLongClick();
            return true;
        }
        int keyCode = getKeyCodeByView(v);
        mRemoter.send(keyCode, MotionEvent.ACTION_DOWN);
        GoGo.sendEmptyMessage(keyCode);
        return true;
    }

    /**
     * BIU键长按事件
     */
    void onBiuLongClick() {
        mBiu.start();  //TODO 去掉语音
    }

    private View.OnTouchListener getOnBiuTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (longPressing && MotionEvent.ACTION_UP == event.getAction()) {
                    longPressing = false;
                    mBiu.release(); //TODO 去掉语音
                }
                return false;
            }
        };

    }

    private View.OnClickListener getOnBiuClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playSound();
                mRemoter.send(KeyCode.BIU, MotionEvent.ACTION_DOWN);
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBiu.destroy();
        mSoundPool.release();
    }
}
