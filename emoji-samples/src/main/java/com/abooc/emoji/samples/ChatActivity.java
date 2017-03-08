package com.abooc.emoji.samples;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.abooc.emoji.EmojiBuilder;
import com.abooc.emoji.EmojiCache;
import com.abooc.emoji.samples.history.HistoryActivity;
import com.abooc.emoji.widget.ChatWidget;
import com.abooc.plugin.about.AboutActivity;
import com.abooc.util.Debug;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    EditText inputBarHint;

    ChatWidget iChatWidget;
    InputBarView mInputBarView;

    RemoveHandler mRemoveHandler = new RemoveHandler();

    class RemoveHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            ChatWidget.delete(inputBarHint);

            sendEmptyMessageDelayed(-1, 300);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        getSupportActionBar().setSubtitle(getClass().getSimpleName());

        addInputBar();

        View longClickView = findViewById(R.id.LongClick);
        longClickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRemoveHandler.removeMessages(-1);
            }
        });
        longClickView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mRemoveHandler.sendEmptyMessageDelayed(-1, 1000);
                return false;
            }
        });


        iChatWidget = (ChatWidget) findViewById(R.id.ChatWidget);
        iChatWidget.setActivity(this);
        iChatWidget.attachTabContent(getSupportFragmentManager());
        iChatWidget.setOnViewerListener(mInputBarView);
        iChatWidget.dismiss();

        inputBarHint = (EditText) findViewById(R.id.inputBarHint);
        inputBarHint.setText(EmojiCache.testMessage);
        inputBarHint.setSelection(inputBarHint.getText().length() - 3);

        findViewById(R.id.inputBar_virtual).setOnClickListener(this);

        attachListView();

        messageLists.add(EmojiCache.testMessage);
        mMessagesAdapter.notifyDataSetChanged();
    }

    ArrayList<String> messageLists = new ArrayList<>();
    ArrayAdapter<String> mMessagesAdapter;
    ListView iListView;

    void attachListView() {
        iListView = (ListView) findViewById(R.id.ListView);
        iListView.setOnItemClickListener(this);

        mMessagesAdapter = new ArrayAdapter<String>(this, 0, messageLists) {
            @NonNull
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
                }


                String message = getItem(position);

                CharSequence emojiCharSequence = EmojiBuilder.toEmojiCharAll(getContext(), message, EmojiCache.getCache());

                TextView textView = (TextView) convertView;
                textView.setText(emojiCharSequence);
                return convertView;
            }
        };
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
//        iChatWidget.showKeyboard();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Debug.anchor("点击 " + position);
    }

    public void onSendEvent(View view) {
        inputBarHint.setText(null);

        Editable text = mInputBarView.getText();
        if (!TextUtils.isEmpty(text)) {
            inputBarHint.setHint(text);

            mMessagesAdapter.insert(text.toString(), mMessagesAdapter.getCount());
            iListView.smoothScrollToPosition(mMessagesAdapter.getCount());

            mInputBarView.setText(null);
        }
    }

    @Override
    public void onBackPressed() {
        if (!iChatWidget.onBackPressed()) {
            super.onBackPressed();
        }
    }
}

