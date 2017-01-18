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
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.abooc.emoji.Keyboard;
import com.abooc.emoji.R;
import com.abooc.emoji.chat.EmojiAddFragment;
import com.abooc.emoji.chat.EmojiFragment;
import com.abooc.emoji.chat.GiftsFragment;
import com.abooc.emoji.chat.GridViewer;
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
                    //超过200时，一般为显示虚拟键盘事件
                    if (!mKeyboardShown) {
                        onKeyboardShown();
                    }

                    mKeyboardShown = true;

                } else {
                    //小于200时，为不显示虚拟键盘或虚拟键盘隐藏
                    if (mKeyboardShown) {
                        onKeyboardHidden();
                    }

                    mKeyboardShown = false;
                }
            }
        });
    }

    public void setOnViewerListener(OnViewerListener listener) {
        mOnViewerListener = listener;
    }

    @Override
    protected void onFinishInflate() {

        setOnClickListener(this);

        View inputBarLayout = getChildAt(1);
        mEditText = (EditText) inputBarLayout.findViewById(R.id.inputBar);

        mEmojiconsContainer = findViewById(R.id.emojicons);

        reAddView(inputBarLayout);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        keyboard(this);
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

        mAnimationOut = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_down);

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
            String item = (String) adapter.getItem(position);
            Debug.anchor("position：" + position + ", " + item);

            mEditText.getText().append(item);

        } else if (content instanceof GiftsFragment) {
            Gift item = (Gift) adapter.getItem(position);
            Debug.anchor("position：" + position + ", " + item.name);

            mEditText.getText().append(item.code);

        }

    }

    View mView;

    @Override
    public void onKeyboardShown() {
        Debug.anchor("键盘弹出");

        mOnViewerListener.onShowKeyboard();
        hideEmojiView();
    }

    @Override
    public void onKeyboardHidden() {
        Debug.anchor("键盘隐藏");

        mOnViewerListener.onShowEmojions();
        showEmojiView();
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
        switch (view.getId()) {
            case R.id.emojicons_menu_add:
                fragment = iTabManager.instance(iTabManager.getTabs().get(0));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.emojicons_menu_emojicon:
                fragment = iTabManager.instance(iTabManager.getTabs().get(1));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.emojicons_menu_gifts:
                Tab tab = iTabManager.getTabs().get(2);
                fragment = iTabManager.instance(tab);
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ChatWidget) {

            Keyboard.hideKeyboard(mActivity);
            hide();
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
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        setVisibility(View.GONE);
    }

    public void showEmoji() {
        Debug.anchor();
        Keyboard.hideKeyboard(mActivity);
    }

    public void showKeyboard() {
        Debug.anchor();
        mEditText.requestFocus();
        Keyboard.showKeyboard(mActivity);

    }

    private void showEmojiView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmojiconsContainer.setVisibility(View.VISIBLE);
            }
        }, 150);
    }

    private void hideEmojiView() {
        mEmojiconsContainer.startAnimation(mAnimationOut);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mEmojiconsContainer.setVisibility(View.GONE);
            }
        }, 50);
    }

    public boolean onBackPressed() {
        if (getVisibility() == View.VISIBLE) {
            Keyboard.hideKeyboard(mActivity);
            hide();
            return true;
        }
        return false;
    }

}
