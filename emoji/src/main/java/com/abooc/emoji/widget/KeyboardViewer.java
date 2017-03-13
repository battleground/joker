package com.abooc.emoji.widget;

import android.view.View;
import android.view.ViewTreeObserver;

import com.abooc.util.Debug;

/**
 * Created by dayu on 2017/3/8.
 */

public class KeyboardViewer implements OnKeyboardShownListener {

    // 区分收起键盘意图，切换表情时，收起键盘但不自动关闭；强制收起键盘操作时，自动关闭模块；
    private boolean mCloseAction = true;
    private boolean mKeyboardShown;


    private OnKeyboardShownListener iOnKeyboardShownListener;

    public void setOnKeyboardShownListener(OnKeyboardShownListener listener) {
        iOnKeyboardShownListener = listener;
    }

    public void keyboard(final View rootView) {
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
                    onKeyboardShown();
                }
                mCloseAction = true;
                mKeyboardShown = true;
            }

            void hide() {
                if (mKeyboardShown) {
                    onKeyboardHidden();
                }
                mKeyboardShown = false;
            }
        });
    }

    @Override
    public void onKeyboardShown() {
        Debug.anchor("键盘已弹出");
        if (iOnKeyboardShownListener != null) {
            iOnKeyboardShownListener.onKeyboardShown();
        }
//        hideEmojiView();
    }

    @Override
    public void onKeyboardHidden() {
        Debug.anchor("键盘已收起");
        if (iOnKeyboardShownListener != null) {
            iOnKeyboardShownListener.onKeyboardHidden();
        }
//        if (mCloseAction) {
//            dismiss();
//        } else {
//            showEmojiView();
//        }
    }
}
