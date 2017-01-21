package com.abooc.emoji.test;

import java.io.Serializable;

/**
 * Created by dayu on 2017/1/12.
 */

public class Emoji implements Serializable {

    public String name;
    public String code;
    public String icon;

    Emoji(String name, String code, String icon) {
        this.name = name;
        this.code = code;
        this.icon = icon;
    }

}
