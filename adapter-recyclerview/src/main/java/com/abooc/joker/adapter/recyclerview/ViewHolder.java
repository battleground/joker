package com.abooc.joker.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewParent;

/**
 * Created by author:李瑞宇
 * email:allnet@live.cn
 * on 15-7-23.
 * <p/>
 * RecyclerView统一使用的ViewHolder
 */
public abstract class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener, OnLongClickListener {
    /**
     * 负责Item点击事件
     */
    public interface OnRecyclerItemClickListener {
        /**
         * @param recyclerView RecyclerView
         * @param itemView     元素根布局
         * @param position     the adapter position that the given child view corresponds to.
         */
        void onItemClick(RecyclerView recyclerView, View itemView, int position);
    }

    /**
     * 负责Item中子View点击事件
     */
    public interface OnRecyclerItemChildClickListener {
        /**
         * @param recyclerView RecyclerView
         * @param itemView     元素根布局
         * @param clickView    响应点击事件的View
         * @param position     the adapter position that the given child view corresponds to.
         */
        void onItemChildClick(RecyclerView recyclerView, View itemView, View clickView, int position);
    }

    /**
     * 负责Item长按事件
     */
    public interface OnRecyclerItemLongClickListener {
        void onItemLongClick(RecyclerView recyclerView, View itemView, int position);
    }


    private Context mContext;
    public OnRecyclerItemClickListener mListener;
    public OnRecyclerItemChildClickListener mChildClickListener;
    public OnRecyclerItemLongClickListener mLongClickListener;

    public ViewHolder(View view, OnRecyclerItemClickListener listener) {
        this(view, listener, null, null);
    }

    public ViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
        this(view, listener, childListener, null);
    }

    public ViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemLongClickListener longClickListener) {
        this(view, listener, null, longClickListener);
    }

    public ViewHolder(View view, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener, OnRecyclerItemLongClickListener longClickListener) {
        super(view);
        mListener = listener;
        mChildClickListener = childListener;
        mLongClickListener = longClickListener;
        mContext = view.getContext();
        if (listener != null)
            view.setOnClickListener(this);
        if (longClickListener != null) {
            view.setOnLongClickListener(this);
        }
        onBindView(view);
    }

    public Context getContext() {
        return mContext;
    }

    public abstract void onBindView(View view);

    @Override
    public void onClick(View v) {
        if (mListener != null && v.getId() == itemView.getId()) {
            mListener.onItemClick(findTarget(itemView.getParent()), v, getAdapterPosition());
        } else if (mChildClickListener != null) {
            mChildClickListener.onItemChildClick(findTarget(itemView.getParent()), itemView, v, getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongClickListener != null) {
            mLongClickListener.onItemLongClick(findTarget(itemView.getParent()), v, getAdapterPosition());
        }
        return false;
    }

    private RecyclerView findTarget(ViewParent parent) {
        if (parent instanceof RecyclerView)
            return (RecyclerView) parent;
        else
            return findTarget(parent);
    }
}