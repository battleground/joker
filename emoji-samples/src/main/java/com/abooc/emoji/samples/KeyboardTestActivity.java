package com.abooc.emoji.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.abooc.emoji.EmojiCache;
import com.abooc.emoji.Keyboard;
import com.abooc.emoji.widget.KeyboardViewer;
import com.abooc.emoji.widget.OnKeyboardShownListener;

public class KeyboardTestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_test);

        getSupportActionBar().setSubtitle(getClass().getSimpleName());

//        addInputBar();

        mEmojiconsView = findViewById(R.id.emojicons);
    }

    View mEmojiconsView;

    InputBarViewBuilder iInputBarViewBuilder = new InputBarViewBuilder();

    public void addInputBar() {
        iInputBarViewBuilder.onFinishInflate(findViewById(R.id.inputBarView));
        KeyboardViewer iKeyboardViewer = new KeyboardViewer();
        iKeyboardViewer.keyboard(findViewById(R.id.ZoomView));
        iKeyboardViewer.setOnKeyboardShownListener(new OnKeyboardShownListener() {
            @Override
            public void onKeyboardShown() {

            }

            @Override
            public void onKeyboardHidden() {

            }
        });

        iInputBarViewBuilder.setOnClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.inputBar_show_emojicon:
                        Keyboard.hideKeyboard(KeyboardTestActivity.this);
                        mEmojiconsView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.inputBar_show_keyboard:
                        EditText editText = iInputBarViewBuilder.getEditText();
                        editText.requestFocus();
                        Keyboard.showKeyboard(KeyboardTestActivity.this, editText);
//                        mEmojiconsView.setVisibility(View.GONE);
                        break;
                }
            }
        });
        iInputBarViewBuilder.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    onSendEvent(v);
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
//        if (!iChatWidget.onBackPressed()) {
//            super.onBackPressed();
//        }
    }
}

