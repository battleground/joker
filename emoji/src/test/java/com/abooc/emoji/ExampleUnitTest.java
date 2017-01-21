package com.abooc.emoji;

import com.abooc.emoji.test.EmojiCache;

import org.junit.Test;

import java.util.HashMap;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


    @Test
    public void test_buildEmojis() throws Exception {

        HashMap<String, String> namesMap = EmojiCache.buildNamesMap();

        int count = namesMap.size();
        int pageCount = getPage(count, 20);

        out("共" + pageCount + "页，每页" + 20 + "条。\n");

        String[] names = new String[count];
        namesMap.keySet().toArray(names);

        for (int page = 1; page <= pageCount; page++) {

            int pageSize = page < pageCount ? 20 : (count % 20);

            out("\n第" + page + "页，共" + pageSize + "条。");

            for (int index = 0; index < pageSize; index++) {

                String name = names[index + ((page - 1) * 20)];
                String icon = namesMap.get(name);

                out(name + ":" + icon);
            }

        }
    }


    static int getPage(int count, int pageSize) {
        int page = (count + (pageSize - 1)) / pageSize;
        return page;
    }

    static void out(String s) {
        System.out.println(s);
    }

    @Test
    public void addition_isCorrect() throws Exception {

        String s = "[emoji_bizui.png][emoji_chijing.png][emoji_daku.png][emoji_dangao.png][emoji_daxiao.png][emoji_deyi.png][emoji_diuxie.png][emoji_hongbao.png][emoji_huaixiao.png][emoji_jianxiao.png][emoji_jingxia.png][emoji_kanhaoni.png][emoji_keai.png][emoji_kelian.png][emoji_kongbu.png][emoji_kuxiao.png][emoji_liubixue.png][emoji_liuhan.png][emoji_nu.png][emoji_outu.png][emoji_pokoudama.png][emoji_tiaopi.png]";

        String all = s.replaceAll("\\.png]\\[", "\", \"");

        String[] a = {"emoji_bizui", "emoji_chijing"};
        String[] b = {
                "emoji_bizui", "emoji_chijing", "emoji_daku", "emoji_dangao", "emoji_daxiao", "emoji_deyi", "emoji_diuxie",
                "emoji_hongbao", "emoji_huaixiao", "emoji_jianxiao", "emoji_jingxia", "emoji_kanhaoni", "emoji_keai", "emoji_kelian",
                "emoji_kongbu", "emoji_kuxiao", "emoji_liubixue", "emoji_liuhan", "emoji_nu", "emoji_outu", "emoji_pokoudama",
                "emoji_tiaopi"
        };

        System.out.println(all);

    }
}