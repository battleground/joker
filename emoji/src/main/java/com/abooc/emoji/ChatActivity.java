package com.abooc.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
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

import com.abooc.plugin.about.AboutActivity;
import com.abooc.util.Debug;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    public static String testMessage = "这是一条[微笑]的表情、[安卓][安卓]2个表情。";
    public static String pattern = "\\[[^\\[\\[]+\\]";

    enum Emojicon {
        微笑(R.drawable.ic_emoji_smile),
        安卓(R.drawable.ic_emoji_android);

        int value;

        Emojicon(int value) {
            this.value = value;
        }

        public String code() {
            return "[" + name() + "]";
        }
    }

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
        SpannableStringBuilder buildMessage = buildMessage(this, testMessage);
        messageHistory.setText(buildMessage);

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

        SpannableStringBuilder builder = buildMessage(this, toString);
        messageHistory.setText(builder);

    }

    static SpannableStringBuilder buildMessage(Context context, String message) {
        Debug.anchor(message);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(message);

        HashMap<int[], String> indexMap = findIndex(message);
        Iterator<int[]> iterator = indexMap.keySet().iterator();

        for (int i = 0; i < indexMap.size(); i++) {
            int[] indexes = iterator.next();
            String emoji = indexMap.get(indexes);
            Bitmap bitmap = findBitmap(emoji, Emoji.emotionsBitmap);
            ImageSpan imageSpan = new ImageSpan(context, bitmap);
            spannableString.setSpan(imageSpan, indexes[0], indexes[1], Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return spannableString;
    }

    public void onEmojiSmileEvent(View view) {
        buildEmoji(Emojicon.微笑);
    }

    public void onEmojiAndroidEvent(View view) {
        buildEmoji(Emojicon.安卓);
    }

    void buildEmoji(Emojicon emojicon) {
        String code = emojicon.code();

        int selectionStart = mInputBarView.getSelectionStart();
        int selectionEnd = mInputBarView.getSelectionEnd();

        if (selectionEnd > selectionStart) {
            inputBarHint.getText().replace(selectionStart, selectionEnd, code);
        } else {
            inputBarHint.getText().insert(selectionStart, code);
        }

        Bitmap bitmap = findBitmap(code, Emoji.emotionsBitmap);
        ImageSpan imageSpan = new ImageSpan(this, bitmap);
        SpannableStringBuilder spannableString = new SpannableStringBuilder(code);
        spannableString.setSpan(imageSpan, 0, code.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        if (selectionEnd > selectionStart) {
            mInputBarView.getText().replace(selectionStart, selectionEnd, spannableString);
        } else {
            mInputBarView.getText().insert(selectionStart, spannableString);
        }
    }

    static Bitmap findBitmap(String emoji, Map<String, Bitmap> bitmapMap) {
        Bitmap bitmap = bitmapMap.get(emoji);
        return bitmap;
    }

    public static HashMap<int[], String> findIndex(String message) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(message);

        HashMap<int[], String> map = new HashMap<>();
        while (m.find()) {
            map.put(new int[]{m.start(), m.end()}, m.group(0));
            System.out.println("From map: " + m.group(0) + ", [" + m.start() + " - " + m.end() + "]");
        }
        System.out.println("\n");
        return map;
    }
}

