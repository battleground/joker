package com.abooc.joker.adapter.recyclerview;

import android.view.View;

/**
 * @author zhangjunpu
 * @date 2017/1/5
 */

public abstract class BaseViewHolder<T> extends ViewHolder {

    public BaseViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener) {
        super(itemLayoutView, listener);
    }

    public BaseViewHolder(View itemLayoutView, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
        super(itemLayoutView, listener, childListener);
    }

    public abstract void bindData(T t);
}
