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

    public InputBarView(View view) {
        inputBar = (EditText) view.findViewById(R.id.inputBar);
        inputBar_show_emojicon = view.findViewById(R.id.inputBar_show_emojicon);
        inputBar_show_keyboard = view.findViewById(R.id.inputBar_show_keyboard);
    }

    public void setOnClickEvent(View.OnClickListener listener) {
        mOnClickListener = listener;
        inputBar_show_emojicon.setOnClickListener(listener);
        inputBar_show_keyboard.setOnClickListener(listener);
    }

    public void showKeyboard() {
        inputBar_show_emojicon.setVisibility(View.GONE);
        inputBar_show_keyboard.setVisibility(View.VISIBLE);
    }

    public void showEmojicon() {
        inputBar_show_keyboard.setVisibility(View.GONE);
        inputBar_show_emojicon.setVisibility(View.VISIBLE);
    }

    public void setText(CharSequence text) {
        inputBar.setText(text);
    }

    public EditText getEditText() {
        return inputBar;
    }

    public Editable getText() {
        return inputBar.getText();
    }

    public boolean requestFocus() {
        return inputBar.requestFocus();
    }

}
