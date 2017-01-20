package com.abooc.emoji.samples;

import android.app.Application;

import com.abooc.emoji.test.Data;
import com.abooc.plugin.about.About;

/**
 * Created by dayu on 2017/1/12.
 */

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Data.build(getResources());
        About.defaultAbout(this);
    }
}
