package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * on 16/6/6.
 */
public class TouchSweepView extends ImageView {

    public interface OnSweepEventListener {
        void onSweepEvent(int keyCode, int action);
    }

    private float dX;
    private float dY;
    private boolean isClick;

    public TouchSweepView(Context context) {
        super(context);
    }

    public TouchSweepView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchSweepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        setOnTouchListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                dX = event.getX();
                dY = event.getY();
                break;
            }
            case MotionEvent.ACTION_UP:
                if (isClick) { // 触控板Touch事件转 onClick
                    onSweepEvent(KeyboardRemoter.KeyCode.OK, MotionEvent.ACTION_UP);
                } else {

                    float x = event.getX();
                    float y = event.getY();

                    if (Math.abs(x - dX) > Math.abs(y - dY)) {// 横向
                        if (x > dX) { // →
                            onSweepEvent(KeyboardRemoter.KeyCode.RIGHT, MotionEvent.ACTION_UP);
                        } else { // ←
                            onSweepEvent(KeyboardRemoter.KeyCode.LEFT, MotionEvent.ACTION_UP);
                        }
                    } else { //纵向
                        if (y > dY) { // 下
                            onSweepEvent(KeyboardRemoter.KeyCode.DOWN, MotionEvent.ACTION_UP);
                        } else { // 上
                            onSweepEvent(KeyboardRemoter.KeyCode.UP, MotionEvent.ACTION_UP);
                        }
                    }
                }
                dX = 0;
                dY = 0;
                break;
            case MotionEvent.ACTION_MOVE: {
                float x = event.getX();
                float y = event.getY();

                if (Math.abs(dX - x) > 10 || Math.abs(dY - y) > 10) {
                    isClick = false;
                } else {
                    isClick = true;
                }
                break;
            }
        }
        return true;
//        return super.onTouchEvent(event);
    }

    private OnSweepEventListener mOnSweepEventListener;

    public void setOnSweepEventListener(OnSweepEventListener listener) {
        mOnSweepEventListener = listener;
    }

    private void onSweepEvent(int keyCode, int action) {
        if (mOnSweepEventListener != null) {
            mOnSweepEventListener.onSweepEvent(keyCode, action);
        }
    }

}
