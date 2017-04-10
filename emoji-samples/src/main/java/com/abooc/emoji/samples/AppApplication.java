package com.abooc.emoji.samples;

import android.app.Application;

import com.abooc.emoji.EmojiCache;
import com.abooc.plugin.about.About;
import com.abooc.widget.Toast;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by dayu on 2017/1/12.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.init(this);

        String jsonArray =
                        "[\"[可爱]\",\"[大笑]\",\"[无知]\",\"[苦笑]\",\"[流鼻血]\",\"[色]\",\"[看好你]\",\"[吻]\",\"[白眼]\",\"[吻我别说话]\",\"[鄙视]\",\"[闭嘴]\",\"[吃惊]\",\"[大哭]\",\"[可怜]\",\"[拽]\",\"[捂脸]\",\"[坏笑]\",\"[流汗]\",\"[挖鼻孔]\"," +
                        "\"[双眼发亮]\",\"[衰人]\",\"[无语]\",\"[轻佻]\",\"[怒]\",\"[惊吓]\",\"[奸笑]\",\"[委屈]\",\"[丢鞋]\",\"[调皮]\",\"[挨打]\",\"[阿呆]\",\"[得意]\",\"[晕]\",\"[恐怖]\",\"[叹气]\",\"[睡觉]\",\"[亲亲]\",\"[破口大骂]\",\"[呕吐]\"," +
                        "\"[太阳]\",\"[奥特曼]\",\"[红包]\",\"[月亮]\",\"[芝士]\",\"[蛋糕]\",\"[吻]\"]";

        Gson gson = new Gson();
        String[] array = gson.fromJson(jsonArray, String[].class);

        HashMap<String, String> namesMap = EmojiCache.buildNamesMap();
        EmojiCache.buildCache(getResources(), getPackageName(), array, namesMap);
        About.defaultAbout(this);
    }
}
