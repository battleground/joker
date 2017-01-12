package com.abooc.emoji;

import android.text.SpannableStringBuilder;

/**
 * Created by dayu on 2017/1/12.
 */

public class Message {
    String message;
    SpannableStringBuilder spannableMessage;


    boolean hasBuild() {
        return spannableMessage != null;
    }
}
