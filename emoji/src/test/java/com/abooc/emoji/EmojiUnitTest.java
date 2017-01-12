package com.abooc.emoji;

import com.abooc.emoji.EmojiActivity;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmojiUnitTest {

    String message = EmojiActivity.testMessage;

    String pattern = EmojiActivity.pattern;

    static Map<String, Integer> emotions = new HashMap<>();

    static {
        emotions.put("[微笑]", com.bftv.emoji.R.drawable.ic_emoji_smile);
        emotions.put("[安卓]", com.bftv.emoji.R.mipmap.ic_launcher);
    }

    @Test
    public void test_string() throws Exception {

        System.out.println("原数据：" + message);

        message = message.replaceAll("\\[微笑\\]", "[表情]");
        System.out.println("新数据：" + message);

        message = message.replaceAll("\\[安卓\\]", "[表情]");
        System.out.println("新数据：" + message);

    }

    @Test
    public void test_emoji() throws Exception {

        System.out.println("原数据：" + message);

        String replace = replace(message, "[表情]");

        System.out.println("新数据：" + replace);

    }

    String replace(String resource, String replacement) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(resource);

        while (m.find()) {
            System.out.println("Found value: " + m.group(0) + ", [" + m.start() + " - " + m.end() + "]");
            resource = m.replaceAll(replacement);
        }
        return resource;
    }


    @Test
    public void test_emotions() throws Exception {

        System.out.println("原数据：" + message);

        HashMap<int[], String> stringHashMap = EmojiActivity.findIndex(message);

        System.out.println("新数据：" + stringHashMap);

    }

}