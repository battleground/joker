package com.abooc.emoji.chat;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by dayu on 2017/1/12.
 */

public class Emoji implements Serializable {

    @Deprecated
    public String description;
    public String code;
    public Bitmap icon;

    public Emoji(String description, String code, Bitmap icon) {
        this.description = description;
        this.code = code;
        this.icon = icon;
    }

}
