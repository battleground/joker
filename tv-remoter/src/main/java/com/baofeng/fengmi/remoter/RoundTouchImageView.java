package com.baofeng.fengmi.remoter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 圆形触摸区域ImageView
 * @author zhangjunpu
 * @date 16/5/9
 */
public class RoundTouchImageView extends ImageView {

    // 圆形点击区域是否按短边计算
    boolean isShortMode = true;

    public RoundTouchImageView(Context context) {
        super(context);
    }

    public RoundTouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundTouchImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 点击区域圆半径是否按照端边计算
     */
    public void setShortMode(boolean shortMode) {
        isShortMode = shortMode;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();

            int width = getWidth();
            int height = getHeight();
            // 半径
            int radius;
            if (isShortMode)
                radius = Math.min(width, height) / 2;
            else
                radius = Math.max(width, height) / 2;
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
