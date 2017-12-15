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

    public BaseViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemLongClickListener longClickListener) {
        super(view, listener, longClickListener);
    }

    public BaseViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener, OnRecyclerItemLongClickListener longClickListener) {
        super(view, listener, childListener, longClickListener);
    }

    @Override
    public void onBindView(View view) {
    }

    public void bindData(T t) {
    }
}
