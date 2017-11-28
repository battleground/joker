package com.abooc.joker.adapter.recyclerview;


import android.content.Context;

import java.util.List;

/**
 * @author zhangjunpu
 *         15/7/27
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerViewAdapter<T> {

    protected Context context;
    public ViewHolder.OnRecyclerItemClickListener listener;
    public ViewHolder.OnRecyclerItemChildClickListener childListener;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setOnRecyclerItemClickListener(ViewHolder.OnRecyclerItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnRecyclerItemChildClickListener(ViewHolder.OnRecyclerItemChildClickListener listener) {
        this.childListener = listener;
    }

    public int getCount() {
        return super.getItemCount();
    }

    public void add(T t) {
        getCollection().add(t);
        notifyDataSetChanged();
    }

    public void add(List<T> list) {
        getCollection().addAll(list);
        notifyDataSetChanged();
    }

    public void addFirst(T t) {
        getCollection().add(0, t);
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
