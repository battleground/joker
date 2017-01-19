package com.abooc.emoji.samples;

/**
 * Created by dayu on 2017/1/12.
 */

public class Message {
    public String message;
    public CharSequence spannableMessage;


    public boolean hasBuild() {
        return spannableMessage != null;
    }
}
