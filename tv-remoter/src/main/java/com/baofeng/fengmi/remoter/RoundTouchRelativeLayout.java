package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 圆形触摸区域RelativeLayout
 * @author zhangjunpu
 * @date 16/5/10
 */
public class RoundTouchRelativeLayout extends RelativeLayout {
    public RoundTouchRelativeLayout(Context context) {
        super(context);
    }

    public RoundTouchRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundTouchRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            int width = getWidth();
            int height = getHeight();
            // 半径
            int radius = Math.min(width, height) / 2;
            // 中心点
            int centerX = width / 2;
            int centerY = height / 2;
            // 点击点到圆心距离
            float a = Math.abs(centerX - x);
            float b = Math.abs(centerY - y);
            int c = (int) Math.sqrt(a * a + b * b);

            if (c - radius > 0) {
                return false;
            }
        }
        return super.dispatchTouchEvent(event);
    }
}
