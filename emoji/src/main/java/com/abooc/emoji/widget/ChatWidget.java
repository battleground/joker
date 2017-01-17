package com.abooc.emoji.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.abooc.emoji.InputBarView;
import com.abooc.emoji.Keyboard;
import com.abooc.emoji.R;
import com.abooc.util.Debug;

/**
 * Created by dayu on 2017/1/17.
 */

public class ChatWidget extends FrameLayout implements View.OnClickListener {

    Activity mActivity;
    InputBarView mInputBarView;
    View mEmojiconsView;

    //    Animation mAnimationIn;
    Animation mAnimationOut;

    public ChatWidget(Context context) {
        this(context, null);
    }

    public ChatWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mAnimationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down);
    }

    public void initUI() {
        setOnClickListener(this);

        mInputBarView = new InputBarView(findViewById(R.id.inputBarView));
        mInputBarView.setOnClickEvent(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.inputBar_show_emojicon:
                        showEmoji();
                        break;
                    case R.id.inputBar_show_keyboard:
                        showKeyboard();
                        break;
                }
            }
        });
        mInputBarView.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
//                    String toString = mInputBarView.getText().toString();
//                    mInputBarView.setText(null);
                }
                onEditorActionListener.onEditorAction(v, actionId, event);
                return false;
            }
        });

        mEmojiconsView = findViewById(R.id.emojicons);
    }

    @Override
    public void onClick(View v) {
        Keyboard.hideKeyboard(mActivity);
        hide();
    }

    TextView.OnEditorActionListener onEditorActionListener;

    public void setOnEditorActionListener(TextView.OnEditorActionListener listener) {
        onEditorActionListener = listener;
    }


    public String getString() {
        return mInputBarView.getText().toString();
    }

    @Override
    protected void onFinishInflate() {
        initUI();
    }

    public void setActivity(Activity activity) {
        mActivity = activity;

    }

    public void show() {
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void showEmoji() {
        Debug.anchor();
        mInputBarView.showKeyboard();
        Keyboard.hideKeyboard(mActivity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmojiconsView.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    public void showKeyboard() {
        Debug.anchor();
        mInputBarView.requestFocus();
        mInputBarView.showEmojicon();
        Keyboard.showKeyboard(mActivity);

        mEmojiconsView.startAnimation(mAnimationOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmojiconsView.setVisibility(View.GONE);
            }
        }, 50);
    }

}
