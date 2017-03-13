package com.abooc.emoji.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.abooc.emoji.R;

public class PointIndicator extends RadioGroup {

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    private int mPointDrawable;

    public PointIndicator(Context context) {
        super(context);
    }

    public PointIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPager(ViewPager viewPager, int pointDrawableRes) {
        setPointDrawable(pointDrawableRes);
        setViewPager(viewPager);
    }

    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null) return;
        mViewPager = viewPager;
        mAdapter = mViewPager.getAdapter();
        int count = mAdapter.getCount();
        if (count <= 0) return;
        addRadioButton(count);
        check(getChildAt(0).getId());
        mViewPager.addOnPageChangeListener(onPageChangeListener);
    }

    public void setPointDrawable(int resid) {
        this.mPointDrawable = resid;
    }

    private void addRadioButton(int count) {
        removeAllViews();
        for (int i = 0; i < count; i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.emoji_point_indicator_radio_default, this, false);
            radioButton.setClickable(false);
            if (mPointDrawable != 0) {
                radioButton.setCompoundDrawablesWithIntrinsicBounds(mPointDrawable, 0, 0, 0);
            }
            addView(radioButton);
        }
    }

    private SimpleOnPageChangeListener onPageChangeListener = new SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            check(getChildAt(position).getId());
        }
    };
}
