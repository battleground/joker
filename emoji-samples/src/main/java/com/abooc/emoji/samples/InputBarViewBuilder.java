package com.abooc.emoji.samples;

import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.abooc.emoji.widget.OnEmojiViewerListener;

/**
 * Created by dayu on 2017/1/12.
 */

public class InputBarViewBuilder implements OnEmojiViewerListener {


    private EditText inputBar;
    private View inputBar_show_emojicon;
    private View inputBar_show_keyboard;

    private View.OnClickListener mOnClickListener;

    private View mInputBarView;

    public void onFinishInflate(View inputBarView) {
        mInputBarView = inputBarView;
        inputBar = (EditText) findViewById(R.id.inputBar);
        inputBar_show_emojicon = findViewById(R.id.inputBar_show_emojicon);
        inputBar_show_keyboard = findViewById(R.id.inputBar_show_keyboard);
    }

    private View findViewById(int id) {
        return mInputBarView.findViewById(id);
    }


    @Override
    public void onShowKeyboard() {
        showEmojicon();
    }

    @Override
    public void onShowEmojions() {
        showKeyboard();
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

    public boolean focus() {
        return inputBar.requestFocus();
    }

}
