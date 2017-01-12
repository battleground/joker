package com.abooc.emoji;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dayu on 2017/1/12.
 */

public class Emoji {

    static Map<String, Bitmap> emotionsBitmap = new HashMap<>();

    static void build(Resources res) {
        Bitmap bitmapSmile = BitmapFactory.decodeResource(res, EmojiActivity.Emojicon.微笑.value);
        Bitmap bitmapAndroid = BitmapFactory.decodeResource(res, EmojiActivity.Emojicon.安卓.value);

        emotionsBitmap.put(EmojiActivity.Emojicon.微笑.code(), bitmapSmile);
        emotionsBitmap.put(EmojiActivity.Emojicon.安卓.code(), bitmapAndroid);
    }

}
