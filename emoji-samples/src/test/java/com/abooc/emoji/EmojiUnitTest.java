package com.abooc.emoji;

import com.google.gson.Gson;

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


    @Test
    public void test_gson_array() throws Exception {

        String jsonArray = "[\"[可爱]\",\"[大笑]\",\"[无知]\",\"[苦笑]\",\"[流鼻血]\",\"[色]\",\"[看好你]\",\"[吻]\",\"[白眼]\",\"[吻我别说话]\",\"[鄙视]\",\"[闭嘴]\",\"[吃惊]\",\"[大哭]\",\"[可怜]\",\"[拽]\",\"[捂脸]\",\"[坏笑]\",\"[流汗]\",\"[抠鼻]\",\"[双眼发亮]\",\"[衰人]\",\"[无语]\",\"[轻佻]\",\"[怒]\",\"[惊吓]\",\"[奸笑]\",\"[委屈]\",\"[丢鞋]\",\"[调皮]\",\"[挨打]\",\"[阿呆]\",\"[得意]\",\"[晕]\",\"[恐怖]\",\"[叹气]\",\"[睡觉]\",\"[亲亲]\",\"[破口大骂]\",\"[呕吐]\",\"[太阳]\",\"[奥特曼]\",\"[红包]\",\"[月亮]\",\"[芝士]\",\"[蛋糕]\",\"[吻]\"]";

        Gson gson = new Gson();
        String[] array = gson.fromJson(jsonArray, String[].class);

        for (String name :
                array) {
            System.out.print(name);
        }


    }
}