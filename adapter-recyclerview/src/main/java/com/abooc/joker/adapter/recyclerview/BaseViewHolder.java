package com.abooc.joker.adapter.recyclerview;

import android.view.View;

/**
 * @author zhangjunpu
 * @date 2017/1/5
 */

public abstract class BaseViewHolder<T> extends ViewHolder {

    public BaseViewHolder(View view, OnRecyclerItemClickListener listener) {
        super(view, listener);
    }

    public BaseViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
        super(view, listener, childListener);
    }

    public abstract void bindData(T t);
}
