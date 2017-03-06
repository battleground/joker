package com.abooc.emoji.samples.test;

import com.abooc.emoji.R;

public enum Emojicon {
    微笑(R.drawable.ic_emoji_action_emojicon),
    安卓(R.drawable.ic_emoji_action_keyboard);

    int value;

    Emojicon(int value) {
        this.value = value;
    }

    public String code() {
        return "[" + name() + "]";
    }
}