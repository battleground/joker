package com.abooc.emoji.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.abooc.emoji.EmojiBuilder;
import com.abooc.emoji.samples.history.HistoryActivity;
import com.abooc.emoji.test.EmojiCache;
import com.abooc.emoji.widget.ChatWidget;
import com.abooc.plugin.about.AboutActivity;

public class EmojiActivity extends AppCompatActivity {


    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";


    EditText inputBarHint;
    EditText inputBar_virtual;
    TextView messageText;

    ChatWidget iChatWidget;
    InputBarView mInputBarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        inputBar_virtual = (EditText) findViewById(R.id.inputBar_virtual);
        inputBarHint = (EditText) findViewById(R.id.inputBarHint);
        messageText = (TextView) findViewById(R.id.message);

        inputBarHint.setText(testMessage);

        inputBar_virtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChatWidget.show();
            }
        });

        CharSequence emojiChar = EmojiBuilder.toEmojiCharSequence(this, testMessage, EmojiCache.getCache());
        messageText.setText(emojiChar);

        addInputBar();

        iChatWidget = (ChatWidget) findViewById(R.id.ChatWidget);
        iChatWidget.setActivity(this);
        iChatWidget.setCharMode(true);
        iChatWidget.attachTabContent(getSupportFragmentManager());
        iChatWidget.setOnViewerListener(mInputBarView);

    }

    public void addInputBar() {
        mInputBarView = (InputBarView) findViewById(R.id.InputBarView);
        mInputBarView.setOnClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.inputBar_show_emojicon:
                        iChatWidget.showEmoji();
                        break;
                    case R.id.inputBar_show_keyboard:
                        iChatWidget.showKeyboard();
                        break;
                }
            }
        });
        mInputBarView.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    onSendEvent(v);
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_emoji, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_histories:
                HistoryActivity.launch(this);
                break;
            case R.id.menu_about:
                AboutActivity.launch(this);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSendEvent(View view) {
        inputBarHint.setText(null);
        inputBar_virtual.setText(null);
        String currentString = mInputBarView.getText().toString();
        inputBarHint.setHint(currentString);

        CharSequence emojiChar = EmojiBuilder.toEmojiCharSequence(this, currentString, EmojiCache.getCache());
        messageText.setText(emojiChar);

        mInputBarView.setText(null);
    }

    public void onEmojiSmileEvent(View view) {
        iChatWidget.show();
        iChatWidget.showEmoji();
        iChatWidget.switchTo(2);
    }

    public void onEmojiAndroidEvent(View view) {
        iChatWidget.show();
        iChatWidget.showKeyboard();
    }

    @Override
    public void onBackPressed() {
        if (!iChatWidget.onBackPressed()) {
            super.onBackPressed();
        }
    }
}

