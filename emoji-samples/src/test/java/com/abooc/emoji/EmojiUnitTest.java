package com.abooc.emoji;

import com.abooc.emoji.test.EmojiCache;

import org.junit.Test;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class EmojiUnitTest {

    String message = EmojiCache.testMessage;

    String PATTERN = EmojiBuilder.EMOJI_PATTERN;


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
        Pattern r = Pattern.compile(PATTERN);
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

        HashMap<int[], String> stringHashMap = findIndex(message);

        System.out.println("新数据：" + stringHashMap);

    }

    private HashMap<int[], String> findIndex(String message) {

        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(message);

        HashMap<int[], String> map = new HashMap<>();
        while (matcher.find()) {
            String emojiChars = matcher.group(0);
            int start = matcher.start();
            int end = matcher.end();

            map.put(new int[]{start, end}, emojiChars);
            System.out.println("From map: " + emojiChars + ", [" + start + " - " + end + "]");
        }
        return map;
    }


}