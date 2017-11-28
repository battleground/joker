package com.abooc.joker.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 多选列表
 *
 * @author zhangjunpu
 *  15/8/10
 */
public abstract class MultipleChoiceRecyclerAdapter<T> extends BaseRecyclerAdapter<T> implements ViewHolder.OnRecyclerItemClickListener {

    protected List<T> mCheckedList = new ArrayList<T>();

    public MultipleChoiceRecyclerAdapter(Context context) {
        super(context);
    }

    public void setCheckedPosition(T object) {
        if (exist(object)) {
            mCheckedList.remove(object);
        } else {
            mCheckedList.add(object);
        }
        notifyDataSetChanged();
    }

    /**
     * 判断是否存在
     *
     * @param object 对象
     * @return true or false
     */
    protected boolean exist(T object) {
        return mCheckedList.contains(object);
    }

    public List<T> getCheckedList() {
        return mCheckedList;
    }

    /**
     * 清空所有已选择的数据
     */
    public void clearCheckedList() {
        mCheckedList.clear();
    }

    /**
     * 获取已选择的数量
     *
     * @return int size
     */
    public int getCheckedCount() {
        return mCheckedList.size();
    }

    /**
     * 选择或取消选择全部
     *
     * @param flag flag
     */
    public void checkedAll(boolean flag) {
        clearCheckedList();
        if (flag) {
            for (T t : getCollection()) {
                mCheckedList.add(t);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        setCheckedPosition(getItem(position));

        if (listener != null) {
            listener.onItemClick(recyclerView, itemView, position);
        }
    }

}
