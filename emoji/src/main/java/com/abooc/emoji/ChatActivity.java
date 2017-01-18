package com.abooc.emoji;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.abooc.emoji.history.HistoryActivity;
import com.abooc.emoji.test.Data;
import com.abooc.emoji.test.Emoji;
import com.abooc.emoji.test.Emojicon;
import com.abooc.emoji.widget.ChatWidget;
import com.abooc.plugin.about.AboutActivity;
import com.abooc.util.Debug;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    EditText inputBarHint;

    ChatWidget iChatWidget;
    InputBarView mInputBarView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        addInputBar();

        iChatWidget = (ChatWidget) findViewById(R.id.ChatWidget);
        iChatWidget.setActivity(this);
        iChatWidget.attachTabContent(getSupportFragmentManager());
        iChatWidget.setOnViewerListener(mInputBarView);

        inputBarHint = (EditText) findViewById(R.id.inputBarHint);
        inputBarHint.setText(Data.testMessage);

        findViewById(R.id.inputBar_virtual).setOnClickListener(this);

        attachListView();

        messageLists.add(Data.testMessage);
        mMessagesAdapter.notifyDataSetChanged();
    }

    ArrayList<String> messageLists = new ArrayList<>();
    ArrayAdapter<String> mMessagesAdapter;
    ListView iListView;

    void attachListView() {
        iListView = (ListView) findViewById(R.id.ListView);
        iListView.setOnItemClickListener(this);

        mMessagesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageLists);
        iListView.setAdapter(mMessagesAdapter);
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
//            chat_widget.setVisibility(View.GONE);
//            return true;
//        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View v) {
        iChatWidget.show();
        iChatWidget.showKeyboard();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Debug.anchor("点击 " + position);
    }

    public void onSendEvent(View view) {
        inputBarHint.setText(null);

        String toString = iChatWidget.getString();
        inputBarHint.setHint(toString);

        mMessagesAdapter.insert(toString, mMessagesAdapter.getCount());
        iListView.smoothScrollToPosition(mMessagesAdapter.getCount());


        mInputBarView.setText(null);
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

    @Override
    public void onBackPressed() {
        if (!iChatWidget.onBackPressed()) {
            super.onBackPressed();
        }
    }
}

