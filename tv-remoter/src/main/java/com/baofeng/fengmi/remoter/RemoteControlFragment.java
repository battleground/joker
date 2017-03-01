package com.baofeng.fengmi.remoter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

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
    private View mBiu; //Biu键
    private View mVolumeUp; //音量加
    private View mVolumeDown; //音量键

    private KeyboardView mKeyboardView;
    private TouchSweepView mTouchSweepEventView;

    private KeyboardRemoter mRemoter;

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
    }

    private void init(View view) {
        mPower = view.findViewById(R.id.power);
        mHome = view.findViewById(R.id.home);
        mBack = view.findViewById(R.id.back);
        mMenu = view.findViewById(R.id.menu);
        mBiu = view.findViewById(R.id.biu);
        mVolumeUp = view.findViewById(R.id.volume_up);
        mVolumeDown = view.findViewById(R.id.volume_down);


        mPower.setOnTouchListener(this);
        mHome.setOnTouchListener(this);
        mBack.setOnTouchListener(this);
        mMenu.setOnTouchListener(this);
        mBiu.setOnTouchListener(this);
        mVolumeUp.setOnTouchListener(this);
        mVolumeDown.setOnTouchListener(this);


        mPower.setOnLongClickListener(this);
        mHome.setOnLongClickListener(this);
        mBack.setOnLongClickListener(this);
        mMenu.setOnLongClickListener(this);
        mBiu.setOnLongClickListener(this);
        mVolumeUp.setOnLongClickListener(this);
        mVolumeDown.setOnLongClickListener(this);

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
        switch (v.getId()) {
            case R.id.power:
                return KeyCode.POWER;
            case R.id.home:
                return KeyCode.HOME;
            case R.id.back:
                return KeyCode.BACK;
            case R.id.menu:
                return KeyCode.MENU;
            case R.id.biu:
                return KeyCode.BIU;
            case R.id.volume_up:
                return KeyCode.VOLUME_UP;
            case R.id.volume_down:
                return KeyCode.VOLUME_DOWN;
            case R.id.up:
                return KeyCode.UP;
            case R.id.down:
                return KeyCode.DOWN;
            case R.id.left:
                return KeyCode.LEFT;
            case R.id.right:
                return KeyCode.RIGHT;
            case R.id.submit:
                return KeyCode.OK;
        }
        return 0;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                longPressing = false;
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
        int keyCode = getKeyCodeByView(v);
        mRemoter.send(keyCode, MotionEvent.ACTION_DOWN);
        GoGo.sendEmptyMessage(keyCode);
        return true;
    }

}
