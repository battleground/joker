package com.abooc.joker.adapter.recyclerview;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 参见 Samples.java
 * </pre>
 * <p>
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 */
public abstract class RecyclerViewAdapter<T> extends RecyclerView.Adapter<ViewHolder> {

    private Collection<T> mArray = new Collection<>();

    public Collection<T> getCollection() {
        return mArray;
    }

    public T getItem(int position) {
        if (mArray == null || position < 0 || position >= getItemCount())
            return null;
        return mArray.get(position);
    }

    public boolean isEmpty() {
        return mArray.isEmpty();
    }

    @Override
    public int getItemCount() {
        return mArray.size();
    }


    public static class Collection<T> extends ArrayList<T> {
        public void update(List<T> array) {
            this.clear();
            this.addAll(array);
        }
    }

}