package com.abooc.emoji.test;

import com.abooc.emoji.R;

import java.util.ArrayList;

/**
 * Created by dayu on 2017/1/12.
 */

public class Data {

    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";


    public static ArrayList<String[]> emojis = new ArrayList<>();

    static {
        String[] strings0 = {
                Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(),
                Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(),
                Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(),
                "退格键"
        };
        String[] strings1 = {
                Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(),
                Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(),
                "退格键"
        };
        String[] strings2 = {
                Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(), Emojicon.微笑.code(),
                "退格键"
        };
        emojis.add(strings0);
        emojis.add(strings1);
        emojis.add(strings2);
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
