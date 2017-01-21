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

public class EmojiCache {

    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";

    private static ArrayList<Emoji[]> emojiLists = new ArrayList<>();


    private static Map<String, Bitmap> emotionsBitmapCache = new HashMap<>();

    public static void buildCache(Resources res) {
        for (int i = 0; i < emojiLists.size(); i++) {
            Emoji[] emojis = emojiLists.get(i);

            build(res, emojis);
        }
    }

    static void build(Resources res, Emoji[] emojis) {
        int length = emojis.length;
        for (int i = 0; i < length; i++) {
            Emoji emoji = emojis[i];
            if (emoji == null) continue;
            int identifier = res.getIdentifier(emoji.icon, "drawable", "com.abooc.emoji.samples");
            Bitmap bitmapSmile = BitmapFactory.decodeResource(res, identifier);
            emotionsBitmapCache.put(emoji.code, bitmapSmile);
        }
    }

    public static Bitmap find(String emojiCode) {
        return emotionsBitmapCache.get(emojiCode);
    }

    public static Map<String, Bitmap> getCache() {
        return emotionsBitmapCache;
    }

    public static ArrayList<Emoji[]> getEmojiLists() {
        return emojiLists;
    }

    public static void initialize() {

        String[] emoji_names0 = {
                "emoji_bizui", "emoji_chijing", "emoji_daku", "emoji_dangao", "emoji_daxiao", "emoji_deyi", "emoji_diuxie",
                "emoji_hongbao", "emoji_huaixiao", "emoji_jianxiao", "emoji_jingxia", "emoji_kanhaoni", "emoji_keai", "emoji_kelian",
                "emoji_kongbu", "emoji_kuxiao", "emoji_liubixue", "emoji_liuhan", "emoji_nu", "emoji_outu"
        };

        String[] emoji_names1 = {
                "emoji_kongbu", "emoji_kuxiao", "emoji_liubixue", "emoji_liuhan", "emoji_nu", "emoji_outu", "emoji_pokoudama",
                "emoji_tiaopi"
        };

        Emoji[] e0 = new Emoji[emoji_names0.length + 1];
        Emoji[] e1 = new Emoji[emoji_names1.length + 1];


        int length = emoji_names0.length;
        for (int index = 0; index < length; index++) {
            String emojiName = emoji_names0[index];
            String emojiCode = emojiName;
            e0[index] = new Emoji(emojiName, "[" + emojiName + "]", emojiCode);
        }

        length = emoji_names1.length;
        for (int index = 0; index < length; index++) {
            String emojiName = emoji_names1[index];
            String emojiCode = emojiName;
            e1[index] = new Emoji(emojiName, "[" + emojiName + "]", emojiCode);
        }

        emojiLists.add(e0);
        emojiLists.add(e1);
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
