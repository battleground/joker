package com.abooc.emoji.test;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.abooc.emoji.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by dayu on 2017/1/12.
 */

public class EmojiCache {

    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";

    private static Emoji[] emojiArrays;


    private static Map<String, Bitmap> emotionsBitmapCache = new HashMap<>();

    public static void buildCache(Resources res, String defPackage) {
        Emoji[] emojis = emojiArrays;
        int length = emojis.length;
        for (int i = 0; i < length; i++) {
            Emoji emoji = emojis[i];
            if (emoji == null) continue;
            int identifier = res.getIdentifier(emoji.icon, "drawable", defPackage);
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

    public static Emoji[] getEmojiArrays() {
        return emojiArrays;
    }

    public static void initialize() {

        HashMap<String, String> namesMap = buildNamesMap();
        Emoji[] array = new Emoji[namesMap.size()];
        Iterator<String> iterator = namesMap.keySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            String name = iterator.next();
            String icon = namesMap.get(name);
            array[i] = new Emoji(name, "[" + name + "]", icon);
            i++;
        }

        emojiArrays = array;

    }

    static void aa(HashMap<String, String> namesMap, int pageSize) {

        int count = namesMap.size();
        int page = getPage(count, 20);

        String[] names = (String[]) namesMap.keySet().toArray();

        for (int i = 0; i < page; i++) {

        }


        Emoji[] emojiArray0 = new Emoji[pageSize + 1];
        Emoji[] e1 = new Emoji[pageSize + 1];


//        int length = emoji_names0.length;
//        for (int index = 0; index < length; index++) {
//            String emojiName = emoji_names0[index];
//            String emojiCode = emojiName;
//            e0[index] = new Emoji(emojiName, "[" + emojiName + "]", emojiCode);
//        }
//
//        length = emoji_names1.length;
//        for (int index = 0; index < length; index++) {
//            String emojiName = emoji_names1[index];
//            String emojiCode = emojiName;
//            e1[index] = new Emoji(emojiName, "[" + emojiName + "]", emojiCode);
//        }
//
//        emojiArrays.add(e0);
//        emojiArrays.add(e1);
    }


    static int getPage(int count, int pageSize) {
        int page = (count + (pageSize - 1)) / pageSize;
        return page;
    }

    public static HashMap<String, String> buildNamesMap() {
        String resources =
                "丢鞋 - xiaomi_diuxie,\n" +
                        "亲亲 - xiaomi_qinqin,\n" +
                        "双眼发亮 - xiaomi_shuangyanfaliang,\n" +
                        "可怜 - xiaomi_kelian,\n" +
                        "可爱 - xiaomi_keai,\n" +
                        "叹气 - xiaomi_tanqi,\n" +
                        "吃惊 - xiaomi_chijing,\n" +
                        "吻 - xiaomi_wen,\n" +
                        "吻我别说话 - xiaomi_wenwobieshuohua,\n" +
                        "呕吐 - xiaomi_outu,\n" +
                        "坏笑 - xiaomi_huaixiao,\n" +
                        "大哭 - xiaomi_daku,\n" +
                        "大笑 - xiaomi_daxiao,\n" +
                        "太阳 - xiaomi_taiyang,\n" +
                        "奥特曼 - xiaomi_aoteman,\n" +
                        "奸笑 - xiaomi_jianxiao,\n" +
                        "委屈 - xiaomi_weiqu,\n" +
                        "得意 - xiaomi_deyi,\n" +
                        "怒 - xiaomi_nu,\n" +
                        "恐怖 - xiaomi_kongbu,\n" +
                        "惊吓 - xiaomi_jingxia,\n" +
                        "拽 - xiaomi_zhuai,\n" +
                        "挖鼻孔 - xiaomi_wabikong,\n" +
                        "挨打 - xiaomi_aida,\n" +
                        "捂脸 - xiaomi_wulian,\n" +
                        "无知 - xiaomi_wuzhi,\n" +
                        "无语 - xiaomi_wuyu,\n" +
                        "晕 - xiaomi_yun,\n" +
                        "月亮 - xiaomi_yueliang,\n" +
                        "流汗 - xiaomi_liuhan,\n" +
                        "流鼻血 - xiaomi_liubixue,\n" +
                        "白眼 - xiaomi_baiyan,\n" +
                        "看好你 - xiaomi_kanhaoni,\n" +
                        "睡觉 - xiaomi_shuijiao,\n" +
                        "破口大骂 - xiaomi_pokoudama,\n" +
                        "红包 - xiaomi_hongbao,\n" +
                        "色 - xiaomi_se,\n" +
                        "芝士 - xiaomi_zhishi,\n" +
                        "苦笑 - xiaomi_kuxiao,\n" +
                        "蛋糕 - xiaomi_dangao,\n" +
                        "衰人 - xiaomi_shuairen,\n" +
                        "调皮 - xiaomi_tiaopi,\n" +
                        "轻佻 - xiaomi_qingtiao,\n" +
                        "鄙视 - xiaomi_bishi,\n" +
                        "闭嘴 - xiaomi_bizui,\n" +
                        "阿呆 - xiaomi_adai";


        String[] split = resources.split(",");

        HashMap<String, String> emojisNameMap = new HashMap<>();
        int length = split.length;
        for (int i = 0; i < length; i++) {
            String[] nameMap = split[i].split("-");
            emojisNameMap.put(nameMap[0].trim(), nameMap[1].trim());
        }
        return emojisNameMap;
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
