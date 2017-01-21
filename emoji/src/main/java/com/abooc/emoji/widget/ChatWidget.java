package com.abooc.emoji.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.abooc.emoji.EmojiBuilder;
import com.abooc.emoji.Keyboard;
import com.abooc.emoji.R;
import com.abooc.emoji.chat.EmojiAddFragment;
import com.abooc.emoji.chat.EmojiFragment;
import com.abooc.emoji.chat.GiftsFragment;
import com.abooc.emoji.chat.GridViewer;
import com.abooc.emoji.test.EmojiCache;
import com.abooc.emoji.test.Emoji;
import com.abooc.emoji.test.Gift;
import com.abooc.joker.tab.Tab;
import com.abooc.joker.tab.TabManager;
import com.abooc.util.Debug;

import static com.abooc.emoji.widget.ChatWidget.Tabs.ADD;
import static com.abooc.emoji.widget.ChatWidget.Tabs.EMOJICON;
import static com.abooc.emoji.widget.ChatWidget.Tabs.GIFTS;

/**
 * Created by dayu on 2017/1/17.
 */

public class ChatWidget extends FrameLayout implements OnKeyboardShownListener, View.OnClickListener, GridView.OnItemClickListener {

    private Activity mActivity;
    private EditText mEditText;

    private View mEmojiconsContainer;

    //    Animation mAnimationIn;
    private Animation mAnimationOut;

    private boolean mKeyboardShown;


    private static final int VIEW_STATUS_KEYBOARD = 1;
    private static final int VIEW_STATUS_EMOJIONS = 2;

    private int mStatus = VIEW_STATUS_KEYBOARD;

    // 区分收起键盘意图，切换表情时，收起键盘但不自动关闭；强制收起键盘操作时，自动关闭模块；
    private boolean mCloseAction = true;

    private OnViewerListener mOnViewerListener = new OnViewerListener() {
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

    void keyboard(final View rootView) {
        //该Activity的最外层Layout

        //给该layout设置监听，监听其布局发生变化事件
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                //比较Activity根布局与当前布局的大小
                int heightDiff = rootView.getRootView().getHeight() - rootView.getHeight();
                if (heightDiff > 200) {
                    show();
                } else {
                    hide();
                }
            }

            void show() {
                if (!mKeyboardShown) {
                    Debug.anchor("键盘已弹出");
                    onKeyboardShown();
                }
                mCloseAction = true;
                mKeyboardShown = true;
            }

            void hide() {
                if (mKeyboardShown) {
                    Debug.anchor("键盘已收起");
                    onKeyboardHidden();
                }
                mKeyboardShown = false;
            }
        });
    }

    public void setOnViewerListener(OnViewerListener listener) {
        mOnViewerListener = listener;
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        Debug.anchor();
        mAnimationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down);
        keyboard((View) this.getParent());
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

    public ChatWidget attachTabContent(FragmentManager manager) {
        iTabManager = new TabManager(getContext(), manager, R.id.ChildTabContent);
        iTabManager
                .add(iTabManager.build(ADD.name, ADD.cls))
                .add(iTabManager.build(EMOJICON.name, EMOJICON.cls))
                .add(iTabManager.build(GIFTS.name, GIFTS.cls));

        Fragment fragment = iTabManager.instance(iTabManager.getTabs().get(0));
        iTabManager.switchTo(null, fragment);
        iTabManager.setOnSwitchListener(new TabManager.OnSwitchListener() {
            @Override
            public void onSwitched(Fragment fragment, Fragment fragment1) {

                GridViewer viewer = (GridViewer) fragment1;
                viewer.setOnItemClickListener(ChatWidget.this);
            }
        });

        findViewById(R.id.emojicons_menu_add).setOnClickListener(this);
        findViewById(R.id.emojicons_menu_emojicon).setOnClickListener(this);
        findViewById(R.id.emojicons_menu_gifts).setOnClickListener(this);
        findViewById(R.id.emojicons_menu_settings).setOnClickListener(this);

        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment content = iTabManager.content;

        ArrayAdapter adapter = (ArrayAdapter) parent.getAdapter();

        if (content instanceof EmojiAddFragment) {
            String item = (String) adapter.getItem(position);
            Debug.anchor("position：" + position + ", " + item);

            mEditText.getText().append(item);

        } else if (content instanceof EmojiFragment) {
            Emoji item = (Emoji) adapter.getItem(position);
            Debug.anchor("position：" + position + ", " + item);

            if (item == null) {
                int selectionStart = mEditText.getSelectionStart();// 获取光标的位置
                Debug.anchor("光标位置:" + selectionStart);
                if (selectionStart > 0) {
                    String body = mEditText.getText().toString();
                    Debug.anchor("文本内容:" + body);
                    if (!TextUtils.isEmpty(body)) {
                        String tempStr = body.substring(0, selectionStart);
                        Debug.anchor("tempStr:" + tempStr);
                        int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                        Debug.anchor("[" + i + ", " + selectionStart + "]");
                        if (i != -1) {
                            CharSequence cs = tempStr.subSequence(i, selectionStart);
//                            if (cs.equals("[fac")) {// 判断是否是一个表情
                            mEditText.getText().delete(i, selectionStart);
//                                return;
//                            }
                        } else {
//                        mEditText.getText().delete(selectionStart - 1, selectionStart);
                        }
                    }
                }

            } else {
                EmojiBuilder.writeEmoji(item.code, mEditText, EmojiCache.getCache());
            }


        } else if (content instanceof GiftsFragment) {
            Gift item = (Gift) adapter.getItem(position);
            Debug.anchor("position：" + position + ", " + item.name);

            EmojiBuilder.writeEmoji(item.code, mEditText, EmojiCache.getCache());
        }

    }

    View mView;

    @Override
    public void onKeyboardShown() {
        hideEmojiView();
    }

    @Override
    public void onKeyboardHidden() {
        if (mCloseAction) {
            dismiss();
        } else {
            showEmojiView();
        }
    }

    enum Tabs {
        ADD("添加表情", EmojiAddFragment.class),
        EMOJICON("表情", EmojiFragment.class),
        GIFTS("礼物", GiftsFragment.class);

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
        if (i == R.id.emojicons_menu_add) {
            fragment = iTabManager.instance(iTabManager.getTabs().get(0));
            iTabManager.switchTo(iTabManager.content, fragment);

        } else if (i == R.id.emojicons_menu_emojicon) {
            fragment = iTabManager.instance(iTabManager.getTabs().get(1));
            iTabManager.switchTo(iTabManager.content, fragment);

        } else if (i == R.id.emojicons_menu_gifts) {
            Tab tab = iTabManager.getTabs().get(2);
            fragment = iTabManager.instance(tab);
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
    }

    public void showEmoji() {
        Debug.anchor("收起键盘");
        mCloseAction = false;
        Keyboard.hideKeyboard(mActivity);
        if (true) {
            showEmojiView();
        }
    }

    public void showKeyboard() {
        mEditText.requestFocus();
        Keyboard.showInputMethod(mActivity, mEditText);
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
                    mOnViewerListener.onShowEmojions();
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
                    mOnViewerListener.onShowKeyboard();
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
