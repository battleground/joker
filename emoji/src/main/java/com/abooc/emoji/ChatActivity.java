package com.abooc.emoji;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.abooc.emoji.history.HistoryActivity;
import com.abooc.emoji.test.Emoji;
import com.abooc.emoji.test.Emojicon;
import com.abooc.plugin.about.AboutActivity;
import com.abooc.util.Debug;

public class ChatActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";

    View emojicons;
    EditText inputBarHint;
    TextView messageHistory;

    InputBarView mInputBarView;
    View chat_inputview;

    View inputWidget;
    //    Animation mAnimationIn;
    Animation mAnimationOut;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mAnimationIn = AnimationUtils.loadAnimation(this, R.anim.push_up_in);
        mAnimationOut = AnimationUtils.loadAnimation(this, R.anim.slide_out_down);

        setContentView(R.layout.activity_chat);
        inputWidget = findViewById(R.id.ChatWidget);
        inputWidget.setVisibility(View.GONE);


        chat_inputview = findViewById(R.id.chat_inputview);

        emojicons = findViewById(R.id.emojicons);
        emojicons.setVisibility(View.GONE);

        inputBarHint = (EditText) findViewById(R.id.inputBarHint);
        messageHistory = (TextView) findViewById(R.id.message_history);

        Emoji.build(getResources());

        inputBarHint.setText(testMessage);
        CharSequence emojiString = EmojiBuilder.toEmojiString(this, testMessage, Emoji.emotionsBitmap);
        messageHistory.setText(emojiString);

        View inputBar_virtual = findViewById(R.id.inputBar_virtual);
        inputBar_virtual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputWidget.setVisibility(View.VISIBLE);
                chat_inputview.findViewById(R.id.inputBar).requestFocus();
                onShowKeyboardEvent(v);

            }
        });


        View inputBar = findViewById(R.id.inputBarView);
        mInputBarView = new InputBarView(inputBar);
        mInputBarView.setOnClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.inputBar_show_emojicon:
                        onShowEmojiEvent(v);
                        break;
                    case R.id.inputBar_show_keyboard:
                        onShowKeyboardEvent(v);
                        break;
                }
            }
        });

        String[] strings = {
                "第一项", "第一项", "第一项", "第一项", "第一项",
                "第2项", "第2项", "第2项", "第2项", "第2项",
                "第3项", "第3项", "第3项", "第3项", "第3项",
                "第4项", "第4项", "第4项", "第4项", "第4项"
        };


        ListView iListView = (ListView) findViewById(R.id.ListView);
        iListView.setOnItemClickListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, strings);
        iListView.setAdapter(adapter);


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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (Keyboard.hideKeyboard(this)) {
//            chat_input_and_emojions.setVisibility(View.GONE);
//            return true;
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Debug.anchor("点击 " + position);
    }

    public void onHideKeyboardEvent(View view) {
        Keyboard.hideKeyboard(this);
        inputWidget.setVisibility(View.GONE);
    }


    public void onShowEmojiEvent(View view) {
        Debug.anchor();
        mInputBarView.showKeyboard();
        Keyboard.hideKeyboard(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                emojicons.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    public void onShowKeyboardEvent(View view) {
        Debug.anchor();
        mInputBarView.showEmojicon();
        Keyboard.showKeyboard(ChatActivity.this);

        emojicons.startAnimation(mAnimationOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                emojicons.setVisibility(View.GONE);
            }
        }, 50);
    }

    public void onSendEvent(View view) {
        inputBarHint.setText(null);
        String toString = mInputBarView.getText().toString();
        inputBarHint.setHint(toString);
        mInputBarView.setText(null);

        CharSequence emojiString = EmojiBuilder.toEmojiString(this, testMessage, Emoji.emotionsBitmap);
        messageHistory.setText(emojiString);

    }

    public void onEmojiSmileEvent(View view) {
        String code = Emojicon.微笑.code();
        CharSequence emojiResult = EmojiBuilder.writeEmoji(code, inputBarHint, Emoji.emotionsBitmap);
        inputBarHint.setText(emojiResult.toString());
    }

    public void onEmojiAndroidEvent(View view) {
        String code = Emojicon.安卓.code();
        CharSequence emojiResult = EmojiBuilder.writeEmoji(code, inputBarHint, Emoji.emotionsBitmap);
        inputBarHint.setText(emojiResult.toString());
    }

}

