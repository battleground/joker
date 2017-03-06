package com.abooc.emoji.chat;

import java.io.Serializable;

/**
 * Created by dayu on 2017/1/12.
 */

public class Emoji implements Serializable {

    public String name;
    public String code;
    public String icon;

    public Emoji(String name, String code, String icon) {
        this.name = name;
        this.code = code;
        this.icon = icon;
    }

}
