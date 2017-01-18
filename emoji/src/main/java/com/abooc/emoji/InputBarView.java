package com.abooc.emoji;

import android.content.Context;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.abooc.emoji.widget.OnViewerListener;

/**
 * Created by dayu on 2017/1/12.
 */

public class InputBarView extends LinearLayout implements OnViewerListener {


    private EditText inputBar;
    private View inputBar_show_emojicon;
    private View inputBar_show_keyboard;

    private View.OnClickListener mOnClickListener;


    public InputBarView(Context context) {
        this(context, null, 0);
    }

    public InputBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray a = context.obtainStyledAttributes(
//                attrs, R.styleable.InputBarView, defStyleAttr, 0);
//        a.recycle();
        LayoutInflater.from(context).inflate(R.layout.inputbar, this, true);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onFinishInflate() {
        inputBar = (EditText) findViewById(R.id.inputBar);
        inputBar_show_emojicon = findViewById(R.id.inputBar_show_emojicon);
        inputBar_show_keyboard = findViewById(R.id.inputBar_show_keyboard);
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
