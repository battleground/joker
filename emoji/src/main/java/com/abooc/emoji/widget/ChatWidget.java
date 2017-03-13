package com.abooc.emoji.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.abooc.emoji.Deleter;
import com.abooc.emoji.EmojiBuilder;
import com.abooc.emoji.EmojiCache;
import com.abooc.emoji.Keyboard;
import com.abooc.emoji.R;
import com.abooc.emoji.chat.Emoji;
import com.abooc.emoji.chat.EmojiFragment;
import com.abooc.emoji.chat.GridViewer;
import com.abooc.joker.tab.TabManager;
import com.abooc.util.Debug;

import static com.abooc.emoji.widget.ChatWidget.Tabs.EMOJICON;

/**
 * 聊天控件
 * <p>
 * Created by dayu on 2017/1/17.
 */

public class ChatWidget extends FrameLayout implements View.OnClickListener, GridView.OnItemClickListener, GridView.OnItemLongClickListener {

    public interface OnShownListener {

        void onOpen();

        void onClosed();

    }

    private Activity mActivity;
    private EditText mEditText;

    private OnShownListener iOnShownListener;
    private View mEmojiconsContainer;

    //    Animation mAnimationIn;
    private Animation mAnimationOut;

    private boolean mCharMode = false;

    private static final int VIEW_STATUS_KEYBOARD = 1;
    private static final int VIEW_STATUS_EMOJIONS = 2;

    private int mStatus = VIEW_STATUS_KEYBOARD;


    private OnEmojiViewerListener mOnEmojiViewerListener = new OnEmojiViewerListener() {
        @Override
        public void onShowKeyboard() {

        }

        @Override
        public void onShowEmojions() {

        }
    };

    public ChatWidget(Context context) {
        this(context, null);
    }

    public ChatWidget(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChatWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.chat_widget, this, true);

    }

    public void setCharMode(boolean enable) {
        mCharMode = enable;
    }


    public void setOnShowListener(OnShownListener listener) {
        iOnShownListener = listener;
    }

    public void setOnViewerListener(OnEmojiViewerListener listener) {
        mOnEmojiViewerListener = listener;
    }

    @Override
    protected void onFinishInflate() {

        setOnClickListener(this);

        ViewGroup inputBarLayout = (ViewGroup) getChildAt(1);

        ViewGroup inputRoot = (ViewGroup) inputBarLayout.getChildAt(0);
        int childCount = inputRoot.getChildCount();

        for (int i = 0; i < childCount; i++) {
            View view = inputRoot.getChildAt(i);
            if (view instanceof EditText) {
                mEditText = (EditText) view;
                break;
            }
        }

        mEditText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard();
            }
        });

        mEmojiconsContainer = findViewById(R.id.emojicons);

        reAddView(inputBarLayout);

    }

    KeyboardViewer mKeyboardViewer = new KeyboardViewer();

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Debug.anchor();
        mAnimationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down);
        mKeyboardViewer.keyboard((View) this.getParent());
    }

    private void reAddView(View view) {
        FrameLayout layout = (FrameLayout) findViewById(R.id.InputBarLayout);

        ViewParent parent = view.getParent();
        ViewGroup root = (ViewGroup) parent;
        root.removeView(view);
        layout.addView(view);
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    private TabManager iTabManager;

    public TabManager attachTabContent(FragmentManager manager) {
        iTabManager = new TabManager(getContext(), manager, R.id.ChildTabContent);
        iTabManager
                .add(iTabManager.build(EMOJICON.name, EMOJICON.cls));

        Fragment fragment = iTabManager.instance(iTabManager.getTabs().get(0));
        iTabManager.setOnSwitchListener(new TabManager.OnSwitchListener() {
            @Override
            public void onSwitched(Fragment fragment, Fragment fragment1) {

                GridViewer viewer = (GridViewer) fragment1;
                viewer.setOnItemClickListener(ChatWidget.this);
                viewer.setOnItemLongClickListener(ChatWidget.this);
            }
        });

        findViewById(R.id.emojicons_menu_emojicon).setOnClickListener(this);

        iTabManager.switchTo(null, fragment);
        return iTabManager;
    }

    public void switchTo(int position) {
        Fragment fragment = iTabManager.instance(iTabManager.getTabs().get(position));
        iTabManager.switchTo(iTabManager.content, fragment);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment content = iTabManager.content;

        ArrayAdapter adapter = (ArrayAdapter) parent.getAdapter();

        if (content instanceof EmojiFragment) {
            Emoji item = (Emoji) adapter.getItem(position);

            if (item == null) {
                Deleter.delete(mEditText);
            } else {
                if (mCharMode) {
                    EmojiBuilder.writeEmoji(item.code, mEditText);
                } else {
                    EmojiBuilder.writeEmoji(item.code, mEditText, EmojiCache.getCache());
                }
            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        ArrayAdapter adapter = (ArrayAdapter) parent.getAdapter();
        Emoji item = (Emoji) adapter.getItem(position);
        if (item == null) {

        }
        return true;
    }

    View mView;

    enum Tabs {
        //        ADD("添加表情", EmojiAddFragment.class),
        EMOJICON("表情", EmojiFragment.class);
//        GIFTS("礼物", GiftsFragment.class);

        String name;
        Class<? extends Fragment> cls;

        Tabs(String name, Class<? extends Fragment> cls) {
            this.name = name;
            this.cls = cls;
        }
    }

    public void onClickTab(View view) {
        Fragment fragment;
        int i = view.getId();
        if (i == R.id.emojicons_menu_emojicon) {
            fragment = iTabManager.instance(iTabManager.getTabs().get(0));
            iTabManager.switchTo(iTabManager.content, fragment);

        }
    }

    @Override
    public void onClick(View v) {
        if (v == this) {
            Keyboard.hideKeyboard(mActivity);
            dismiss();
        } else {
            if (mView != null) {
                mView.setSelected(false);
            }
            mView = v;
            v.setSelected(true);

            onClickTab(v);
        }
    }

    public String getString() {
        return mEditText.getText().toString();
    }

    public void show() {
        Debug.anchor("启动模块");
        setVisibility(View.VISIBLE);
        if (mStatus == VIEW_STATUS_KEYBOARD) {
            showKeyboard();
        } else {
            showEmoji();
        }

        if (iOnShownListener != null) {
            iOnShownListener.onOpen();
        }
    }

    public void dismiss() {
        Debug.anchor("关闭模块");
        mEditText.clearFocus();
        setVisibility(View.GONE);
//        if (mStatus == VIEW_STATUS_EMOJIONS) {
//            Debug.anchor("      恢复键盘状态，隐藏表情模块！");
//            mStatus = VIEW_STATUS_KEYBOARD;
//            mEmojiconsContainer.setVisibility(View.GONE);
//        }

        if (iOnShownListener != null) {
            iOnShownListener.onClosed();
        }
    }

    public void showEmoji() {
        Debug.anchor("收起键盘");
//        mCloseAction = false;
        Keyboard.hideKeyboard(mActivity);
        if (true) {
            showEmojiView();
        }
    }

    public void showKeyboard() {
        mEditText.requestFocus();
        Keyboard.showKeyboard(mActivity, mEditText);
        if (true) {
            hideEmojiView();
        }
    }

    private Handler mHandler = new Handler();

    private void showEmojiView() {
        mStatus = VIEW_STATUS_EMOJIONS;
        if (mEmojiconsContainer.getVisibility() != View.VISIBLE) {
            Debug.anchor("显示表情模块");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEmojiconsContainer.setVisibility(View.VISIBLE);
                    mOnEmojiViewerListener.onShowEmojions();
                }
            }, 150);
        }
    }

    private void hideEmojiView() {
        mStatus = VIEW_STATUS_KEYBOARD;
        if (mEmojiconsContainer.getVisibility() == View.VISIBLE) {
            Debug.anchor("隐藏表情模块");
            mEmojiconsContainer.startAnimation(mAnimationOut);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mEmojiconsContainer.setVisibility(View.GONE);
                    mOnEmojiViewerListener.onShowKeyboard();
                }
            }, 50);
        }
    }

    public boolean onBackPressed() {
        if (getVisibility() == View.VISIBLE) {
            Keyboard.hideKeyboard(mActivity);
            dismiss();
            return true;
        }
        return false;
    }

}
