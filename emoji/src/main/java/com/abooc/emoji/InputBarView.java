package com.abooc.emoji;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

/**
 * Created by dayu on 2017/1/12.
 */

public class InputBarView {


    EditText inputBar;
    View inputBar_show_emojicon;
    View inputBar_show_keyboard;

    View.OnClickListener mOnClickListener;

    InputBarView(View view) {
        inputBar = (EditText) view.findViewById(R.id.inputBar);
        inputBar_show_emojicon = view.findViewById(R.id.inputBar_show_emojicon);
        inputBar_show_keyboard = view.findViewById(R.id.inputBar_show_keyboard);
    }

    void setOnClickEvent(View.OnClickListener listener) {
        mOnClickListener = listener;
        inputBar_show_emojicon.setOnClickListener(listener);
        inputBar_show_keyboard.setOnClickListener(listener);
    }

    void showKeyboard() {
        inputBar_show_emojicon.setVisibility(View.GONE);
        inputBar_show_keyboard.setVisibility(View.VISIBLE);
    }

    void showEmojicon() {
        inputBar_show_keyboard.setVisibility(View.GONE);
        inputBar_show_emojicon.setVisibility(View.VISIBLE);
    }

    void setText(CharSequence text) {
        inputBar.setText(text);
    }

    Editable getText() {
        return inputBar.getText();
    }

    int getSelectionStart() {
        return inputBar.getSelectionStart();
    }

    int getSelectionEnd() {
        return inputBar.getSelectionEnd();
    }

}
