package com.abooc.emoji.test;

import com.abooc.emoji.Message;
import com.abooc.emoji.R;

import java.util.ArrayList;

import static com.abooc.emoji.test.Emojicon.微笑;

/**
 * Created by dayu on 2017/1/12.
 */

public class Data {

    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";

    public static MessageBuilder mMessageBuilder = new MessageBuilder();

    public static class MessageBuilder {

        public ArrayList<Message> list = new ArrayList<>();

        public boolean add(String e) {
            Message message = new Message();
            message.message = e.toString();
            return list.add(message);
        }
    }


    public static ArrayList<String[]> emojis = new ArrayList<>();

    static {
        String[] strings0 = {
                微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(),
                微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(),
                微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(),
                "退格键"
        };
        String[] strings1 = {
                微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(),
                微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(),
                "退格键"
        };
        String[] strings2 = {
                微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(), 微笑.code(),
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

    public static void buildMessages() {

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

        mMessageBuilder.add("的的发" + Emojicon.安卓.code() + "生的");
        mMessageBuilder.add("他人同意我" + 微笑.code());
        mMessageBuilder.add("图方便" + Emojicon.安卓.code() + "的");
        mMessageBuilder.add("骨灰级" + Emojicon.安卓.code() + 微笑.code() + 微笑.code() + "的挂号费嘎多发的发生大发的说法二");
        mMessageBuilder.add("风风光光" + Emojicon.安卓.code() + 微笑.code() + Emojicon.安卓.code() + "还开会进口红酒");
        mMessageBuilder.add("科技局囖Pui儿童期而非大幅度发格式化fghghjy7");
        mMessageBuilder.add("" + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code() + 微笑.code());

    }

}
