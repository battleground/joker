package com.abooc.emoji.test;

import com.abooc.emoji.R;

public enum Emojicon {
    微笑(R.drawable.ic_action_emoji),
    安卓(R.drawable.ic_action_keyboard);

    int value;

    Emojicon(int value) {
        this.value = value;
    }

    public String code() {
        return "[" + name() + "]";
    }
}