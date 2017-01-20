package com.abooc.emoji.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.abooc.emoji.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dayu on 2017/1/12.
 */

public class Data {

    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";


    public static Map<String, Bitmap> emotionsBitmapCache = new HashMap<>();

    public static void build(Resources res) {
        Emoji[] emojis = Data.emojis.get(0);
        int length = emojis.length;
        for (int i = 0; i < length; i++) {
            Emoji emoji = emojis[i];
            int identifier = res.getIdentifier(emoji.icon, "drawable", "com.abooc.emoji.samples");
            Bitmap bitmapSmile = BitmapFactory.decodeResource(res, identifier);
            emotionsBitmapCache.put(emoji.code, bitmapSmile);
        }
    }


    public static ArrayList<Emoji[]> emojis = new ArrayList<>();

    static {
        String[] emoji_names = {"大笑", "坏笑", "大哭", "奥特曼"};

        Emoji[] e0 = new Emoji[emoji_names.length];
        Emoji[] e1 = new Emoji[emoji_names.length];


        int index = 0;
        e0[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "daxiao");
        e1[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "daxiao");

        ++index;
        e0[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "huaixiao");
        e1[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "huaixiao");


        ++index;
        e0[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "daku");
        e1[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "daku");


        ++index;
        e0[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "aoteman");
        e1[index] = new Emoji(emoji_names[index], "[" + emoji_names[index] + "]", "aoteman");


        emojis.add(e0);
        emojis.add(e1);

    }


    public static ArrayList<Gift[]> gifts = new ArrayList<>();

    static {

        Gift[] gifts0 = new Gift[7];
        for (int i = 0; i < gifts0.length; i++) {

            Gift gift = new Gift();
            gift.name = "战斧" + i;
            gift.code = "[zhanfu]";
            gift.coin = "200 金币";
            gift.iconId = R.drawable.ic_emoji_gifts_zhanfu;

            gifts0[i] = gift;
        }

        Gift[] gifts1 = new Gift[5];
        for (int i = 0; i < gifts1.length; i++) {

            Gift gift = new Gift();
            gift.name = "战斧" + i;
            gift.code = "[zhanfu]";
            gift.coin = "200 金币";
            gift.iconId = R.drawable.ic_emoji_gifts_zhanfu;

            gifts1[i] = gift;
        }

        gifts.add(gifts0);
        gifts.add(gifts1);
    }


}
