package com.abooc.joker.adapter.recyclerview;


import android.content.Context;

import com.abooc.joker.adapter.recyclerview.ViewHolder.OnRecyclerItemChildClickListener;
import com.abooc.joker.adapter.recyclerview.ViewHolder.OnRecyclerItemClickListener;

import java.util.List;

/**
 * @author zhangjunpu
 * @date 15/7/27
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerViewAdapter<T> {

    protected Context mContext;
    public OnRecyclerItemClickListener mListener;
    public OnRecyclerItemChildClickListener mChildListener;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener listener) {
        this.mListener = listener;
    }

    public void setOnRecyclerItemChildClickListener(OnRecyclerItemChildClickListener listener) {
        this.mChildListener = listener;
    }


    public void add(T t) {
        getCollection().add(t);
        notifyDataSetChanged();
    }

    public void addFirst(List<T> list) {
        getCollection().addAll(0, list);
        notifyDataSetChanged();
    }

    public void update(List<T> array) {
        getCollection().update(array);
        notifyDataSetChanged();
    }

    public void remove(int position) {
        getCollection().remove(position);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        getCollection().remove(t);
        notifyDataSetChanged();
    }

    public void replace(int position, T t) {
        if (position >= 0 && position < getCollection().size()) {
            getCollection().set(position, t);
        }
        notifyDataSetChanged();
    }

    public boolean contains(T t) {
        if (!isEmpty()) {
            return getCollection().contains(t);
        }
        return false;
    }

    public void clear() {
        getCollection().clear();
        notifyDataSetChanged();
    }
}