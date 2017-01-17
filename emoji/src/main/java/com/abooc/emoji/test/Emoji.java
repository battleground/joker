package com.abooc.emoji.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dayu on 2017/1/12.
 */

public class Emoji {

    public static Map<String, Bitmap> emotionsBitmap = new HashMap<>();

    public static void build(Resources res) {
        Bitmap bitmapSmile = BitmapFactory.decodeResource(res, Emojicon.微笑.value);
        Bitmap bitmapAndroid = BitmapFactory.decodeResource(res, Emojicon.安卓.value);

        emotionsBitmap.put(Emojicon.微笑.code(), bitmapSmile);
        emotionsBitmap.put(Emojicon.安卓.code(), bitmapAndroid);
    }

}
