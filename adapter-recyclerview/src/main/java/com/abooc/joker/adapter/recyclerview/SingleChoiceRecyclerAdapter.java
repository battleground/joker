package com.abooc.joker.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 单选列表
 * @author zhangjunpu
 *  15/8/10
 */
public abstract class SingleChoiceRecyclerAdapter<T> extends BaseRecyclerAdapter<T> implements ViewHolder.OnRecyclerItemClickListener {

    protected int mCheckedPosition = -1;

    public SingleChoiceRecyclerAdapter(Context context) {
        super(context);
    }

    public void setCheckedPosition(int position) {
        this.mCheckedPosition = position;
        notifyDataSetChanged();
    }

    public int getCheckedPosition() {
        return mCheckedPosition;
    }

    public T getCheckedItem() {
        if (mCheckedPosition >= 0 && mCheckedPosition < getItemCount()) {
            return getItem(mCheckedPosition);
        }
        return null;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        setCheckedPosition(position);

        if (mListener != null) {
            mListener.onItemClick(recyclerView, itemView, position);
        }
    }

}
