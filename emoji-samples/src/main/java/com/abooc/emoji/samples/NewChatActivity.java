package com.abooc.emoji.samples;

import android.os.Bundle;
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
import com.abooc.emoji.Keyboard;
import com.abooc.emoji.samples.history.HistoryActivity;
import com.abooc.emoji.widget.KeyboardViewer;
import com.abooc.emoji.widget.OnKeyboardShownListener;
import com.abooc.plugin.about.AboutActivity;
import com.abooc.util.Debug;

import java.util.ArrayList;

public class NewChatActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    EditText inputBarHint;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);

        getSupportActionBar().setSubtitle(getClass().getSimpleName());

        addInputBar();

        inputBarHint = (EditText) findViewById(R.id.inputBarHint);
        inputBarHint.setText(EmojiCache.testMessage);
        inputBarHint.setSelection(inputBarHint.getText().length() - 3);

        attachListView();

        messageLists.add(EmojiCache.testMessage);
        messageLists.add(EmojiCache.testMessage);
        messageLists.add(EmojiCache.testMessage);
        messageLists.add(EmojiCache.testMessage);
        mMessagesAdapter.notifyDataSetChanged();


        mEmojiconsView = findViewById(R.id.emojicons);
        mEmojiconsView.setVisibility(View.GONE);
    }

    View mEmojiconsView;
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
                        Keyboard.hideKeyboard(NewChatActivity.this);
                        mEmojiconsView.setVisibility(View.VISIBLE);
                        break;
                    case R.id.inputBar_show_keyboard:
                        EditText editText = iInputBarViewBuilder.getEditText();
                        editText.requestFocus();
                        Keyboard.showKeyboard(NewChatActivity.this, editText);
//                        mEmojiconsView.setVisibility(View.GONE);
                        break;
                }
            }
        });
        iInputBarViewBuilder.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
//        iChatWidget.showKeyboard();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Debug.anchor("点击 " + position);
    }

    public void onSendEvent(View view) {
        inputBarHint.setText(null);

        Editable text = iInputBarViewBuilder.getText();
        if (!TextUtils.isEmpty(text)) {
            inputBarHint.setHint(text);

            mMessagesAdapter.insert(text.toString(), mMessagesAdapter.getCount());
            iListView.smoothScrollToPosition(mMessagesAdapter.getCount());

            iInputBarViewBuilder.setText(null);
        }
    }

    @Override
    public void onBackPressed() {
//        if (!iChatWidget.onBackPressed()) {
//            super.onBackPressed();
//        }
    }
}

