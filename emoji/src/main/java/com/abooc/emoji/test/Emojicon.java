package com.abooc.emoji.test;

import com.abooc.emoji.R;

public enum Emojicon {
    微笑(R.drawable.ic_emoji_smile),
    安卓(R.drawable.ic_emoji_android);

    int value;

    Emojicon(int value) {
        this.value = value;
    }

    public String code() {
        return "[" + name() + "]";
    }
}