package com.abooc.emoji;

import android.app.Application;

import com.abooc.emoji.test.Emoji;
import com.abooc.plugin.about.About;

/**
 * Created by dayu on 2017/1/12.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Emoji.build(getResources());
        About.defaultAbout(this);
    }
}
