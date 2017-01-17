package com.abooc.emoji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.abooc.emoji.history.HistoryActivity;
import com.abooc.emoji.test.Emoji;
import com.abooc.emoji.test.Emojicon;
import com.abooc.emoji.widget.ChatWidget;
import com.abooc.plugin.about.AboutActivity;

public class EmojiActivity extends AppCompatActivity {


    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";


    EditText inputBarHint;
    EditText inputBar;
    TextView messageText;

    ChatWidget iChatWidget;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        inputBar = (EditText) findViewById(R.id.inputBar);
        inputBarHint = (EditText) findViewById(R.id.inputBarHint);
        messageText = (TextView) findViewById(R.id.message);

        Emoji.build(getResources());

        inputBarHint.setText(testMessage);

        CharSequence emojiString = EmojiBuilder.toEmojiCharSequence(this, testMessage, Emoji.emotionsBitmap);
        inputBar.setText(emojiString);
        inputBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iChatWidget.show();
            }
        });

        messageText.setText(emojiString);


        iChatWidget = (ChatWidget) findViewById(R.id.ChatWidget);
        iChatWidget.setActivity(this);
        iChatWidget.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                onSend(v);
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
        String currentString = inputBar.getText().toString();
        inputBarHint.setHint(currentString);
        inputBar.setText(null);

        CharSequence emojiString = EmojiBuilder.toEmojiCharSequence(this, currentString, Emoji.emotionsBitmap);
        messageText.setText(emojiString);
    }

    public void onSend(View view) {
        String toString = iChatWidget.getString();
        inputBarHint.setHint(toString);

        CharSequence emojiString = EmojiBuilder.toEmojiCharSequence(this, toString, Emoji.emotionsBitmap);
        messageText.setText(emojiString);

    }


    public void onEmojiSmileEvent(View view) {
        String code = Emojicon.微笑.code();
        CharSequence emojiResult = EmojiBuilder.writeEmoji(code, inputBar, Emoji.emotionsBitmap);
        inputBarHint.setText(emojiResult.toString());
    }

    public void onEmojiAndroidEvent(View view) {
        String code = Emojicon.安卓.code();
        String emojiResult = EmojiBuilder.writeEmoji(code, inputBar);
        inputBarHint.setText(emojiResult.toString());
    }

}

