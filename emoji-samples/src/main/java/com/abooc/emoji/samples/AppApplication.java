package com.abooc.emoji.samples;

import android.app.Application;

import com.abooc.emoji.EmojiCache;
import com.abooc.plugin.about.About;

/**
 * Created by dayu on 2017/1/12.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        EmojiCache.initialize();
        EmojiCache.buildCache(getResources(), getPackageName());
        About.defaultAbout(this);
    }
}
