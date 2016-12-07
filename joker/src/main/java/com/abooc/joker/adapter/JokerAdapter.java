package com.abooc.joker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by dayu on 2016/12/2.
 */

public class JokerAdapter extends BaseAdapter {


    private Context mContext;
    private int mResId;
    private int mCount;

    ArrayList<Joker> jokers = new ArrayList<>();

    class Joker {
        String id;
        String name;

    }

    public JokerAdapter(Context ctx, int resId, int count) {
        mContext = ctx;
        mResId = resId;
        mCount = count;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(mResId, viewGroup, false);
        }

        return view;
    }
}
