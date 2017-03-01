package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * on 16/6/6.
 */
public class KeyboardView extends RoundTouchRelativeLayout {

    private View mUp; //上
    private View mDown; //下
    private View mLeft; //左
    private View mRight; //右
    private View mSubmit; //确定

    public KeyboardView(Context context) {
        super(context);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mUp = findViewById(R.id.up);
        mDown = findViewById(R.id.down);
        mLeft = findViewById(R.id.left);
        mRight = findViewById(R.id.right);
        mSubmit = findViewById(R.id.submit);

    }

    public void setOnItemKeyTouchListener(View.OnTouchListener listener) {
        mUp.setOnTouchListener(listener);
        mDown.setOnTouchListener(listener);
        mLeft.setOnTouchListener(listener);
        mRight.setOnTouchListener(listener);
        mSubmit.setOnTouchListener(listener);
    }

    public void setOnItemKeyLongClickListener(View.OnLongClickListener listener) {
        mUp.setOnLongClickListener(listener);
        mDown.setOnLongClickListener(listener);
        mLeft.setOnLongClickListener(listener);
        mRight.setOnLongClickListener(listener);
        mSubmit.setOnLongClickListener(listener);
    }
}
