package com.abooc.joker.messageview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * 负责呈现没有获取到数据时的提示
 * </br>
 * 通常作为ListView、GridView等呈现网络数据的布局，在其获取数据失败时的提示页面；
 * <li> 方便loading和非loading状态的提示；
 * <li> 方便获取数据结果的提示，如获取数据为空，获取数据失败，网络错误等提示。
 * </br>
 * </br>
 *
 * @author liruiyu E-mail:allnet@live.cn
 * @version 创建时间：2014-5-14 类说明
 */
public class MessageView extends FrameLayout {

    private ProgressBar mProgressBar;
    private View mMessageLayout;
    private TextView mMessageText;
    private TextView mRetryText;
    private ImageView mImageView;
    private boolean mRetryEnable = false;
    private int mImageResId;

    public MessageView(Context context) {
        this(context, null);
    }

    public MessageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.messageview, this, true);
        mProgressBar = (ProgressBar) view.findViewById(android.R.id.progress);
        mMessageLayout = view.findViewById(R.id.MessageViewLayout);
        mImageView = (ImageView) view.findViewById(R.id.Image);
        mMessageText = (TextView) view.findViewById(R.id.MessageText);
        mRetryText = (TextView) view.findViewById(R.id.RetryText);
    }

    public void setProgressBarStyle(){
        Drawable drawable = getResources().getDrawable(R.drawable.progress_bee);
        mProgressBar.setProgressDrawable(drawable);
    }

    public void setMessageImage(int resId) {
        mImageResId = resId;
        mImageView.setImageResource(resId);
    }

    public void setMessageTextColor(int color) {
        mMessageText.setTextColor(color);
    }

    /**
     * 设置提示的消息内容 </p>该内容会显示在布局中心，如果当前是loading状态，则显示为loading的消息，
     * 如果非loading状态，则作为提示消息。
     *
     * @param text
     */
    public void setMessage(CharSequence text) {
        mRetryText.setVisibility(mRetryText.getVisibility() != VISIBLE ? VISIBLE : GONE);
        mMessageText.setText(text);
    }

    public void setOnRetryListener(OnClickListener retryListener) {
        mRetryEnable = retryListener != null;
        mRetryText.setOnClickListener(retryListener);
    }

    public void setRetryText(String text) {
        mRetryEnable = TextUtils.isEmpty(text);
        mRetryText.setText(text);
    }

    public void setRetryTextColor(int resId) {
        mRetryText.setTextColor(getResources().getColorStateList(resId));
    }

    public void setRetryEnable(boolean enable) {
        mRetryEnable = enable;
        mRetryText.setVisibility(enable ? VISIBLE : GONE);
    }

    /**
     * 启动loading状态
     */
    public void loading() {
        this.setClickable(false);
        mProgressBar.setVisibility(VISIBLE);
        mMessageLayout.setVisibility(GONE);
    }

    /**
     * 停止loading状态
     */
    public void stop() {
        this.setClickable(true);
        mProgressBar.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setVisibility(VISIBLE);
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
        mImageView.setImageResource(0);
    }

    /**
     * 没网络，网络错误，显示msg
     */
    public void noNetwork(String msg) {
        this.setClickable(true);
        mProgressBar.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(msg);
        mMessageText.setVisibility(VISIBLE);
        mRetryEnable = true;
        mRetryText.setVisibility(VISIBLE);
        mImageView.setImageResource(R.drawable.ic_messageview_no_network);
    }

    /**
     * 没网络，网络错误，显示重试按钮
     */
    public void noNetwork() {
        noNetwork("网络出了点小问题，检查一下");
    }

    /**
     * 空数据状态，status为200，但数据为空
     */
    public void empty() {
        empty("暂无数据");
    }

    public void empty(CharSequence message) {
        this.setClickable(true);
        setVisibility(VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(message);
        mMessageText.setVisibility(VISIBLE);
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
        if (mImageResId != 0) {
            mImageView.setImageResource(mImageResId);
        } else {
            mImageView.setImageResource(R.drawable.ic_messageview_no_data);
        }
    }

    /**
     * 数据错误，status不为200，显示错误信息
     */
    public void error(CharSequence msg) {
        this.setClickable(true);
        mProgressBar.setVisibility(View.GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(msg);
        mMessageText.setVisibility(VISIBLE);
        mImageView.setImageResource(R.drawable.ic_messageview_error);
        mRetryText.setVisibility(mRetryEnable ? VISIBLE : GONE);
    }

    /**
     * 准备提示语
     */
    public void prepare(String msg) {
        this.setClickable(true);
        mProgressBar.setVisibility(GONE);
        mMessageLayout.setVisibility(VISIBLE);
        mMessageText.setText(msg);
        mMessageText.setVisibility(VISIBLE);
        mRetryText.setVisibility(GONE);
        mImageView.setImageResource(0);
    }

//    /**
//     * 设置为特殊状态
//     */
//    public void setSpecialStatus(String retryText, OnClickListener onClickListener) {
//        setSpecialStatus(retryText, onClickListener, false);
//    }

//    /**
//     * 设置为特殊状态
//     */
//    public void setSpecialStatus(String retryText, OnClickListener onClickListener, boolean isFullWidth) {
//        setRetryEnable(true);
//        setRetryTextColor(R.color.white);
//        setRetryBaskground(R.drawable.rounded_bg_red);
//        mRetryText.getLayoutParams().width = isFullWidth ? LayoutParams.MATCH_PARENT : dp2px(getContext(), 173);
//        mRetryText.setText(retryText);
//        mRetryText.setOnClickListener(onClickListener);
//    }

    /**
     * 设置为重试状态
     */
    public void setRetryStatus(OnClickListener onClickListener) {
        mRetryText.setText("重新加载");
        mRetryText.setOnClickListener(onClickListener);
    }


//    /**
//     * 返回dp值
//     */
//    public static int dp2px(Context context, int dp) {
//        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
//    }

}
