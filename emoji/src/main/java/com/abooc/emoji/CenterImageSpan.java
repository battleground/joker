package com.abooc.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.style.ImageSpan;

/**
 * 强制控制图片大小与文字大小一致
 * @author zhangjunpu
 * @date 2017/4/20
 */
public class CenterImageSpan extends ImageSpan {

    public CenterImageSpan(Drawable d) {
        super(d);
    }

    public CenterImageSpan(Context context, int resourceId) {
        super(context, resourceId);
    }

    public CenterImageSpan(Context context, Bitmap b) {
        super(context, b);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
        Drawable drawable = getDrawable();
        Rect rect = drawable.getBounds();
        if (fm != null) {
            FontMetricsInt fmPaint = paint.getFontMetricsInt();
            // 获得文字、图片高度
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;
            // 对于这段算法LZ表示也不解，正常逻辑应该同draw中的计算一样但是显示的结果不居中，经过几次调试之后才发现这么算才会居中
            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }

}
