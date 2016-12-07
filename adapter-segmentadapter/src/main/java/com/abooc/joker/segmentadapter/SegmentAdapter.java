package com.abooc.joker.segmentadapter;

import java.util.List;

/**
 * SegmentAdapter 支持分页加载
 *
 * @author liruiyu
 * @2014-5-21
 */
public class SegmentAdapter<T> extends DataAdapter<T> {

    /**
     * 分页加载监听，
     *
     * @author liruiyu
     */
    public interface OnNextPageListener {
        /**
         * 分页回调
         *
         * @param count    当前总数
         * @param nextPage 下一页页码
         * @param position 当前加载位置
         */
        void onNextPage(int count, int nextPage, int position);
    }

    private int page = 1;
    private OnNextPageListener iOnNextPageListener;
    private boolean nextWaiting;

    public SegmentAdapter(List<T> array) {
        super(array);
    }

    /**
     * 设置分页加载回调
     *
     * @param l
     */
    public void setOnNextPageListener(OnNextPageListener l) {
        iOnNextPageListener = l;
    }

    @Override
    public void clear() {
        super.clear();
        page = 1;
    }

    /**
     * 回退上一页
     *
     * @return
     */
    public boolean rollback() {
        if (nextWaiting) {
            page--;
            return true;
        }
        return false;
    }

    /**
     * @return 返回当前页码
     */
    public int getPage() {
        return page;
    }

    @Override
    public boolean add(List<T> array) {
        nextWaiting = false;
        return super.add(array);
    }

    @Override
    public void onLoadPosition(int count, int position) {
        super.onLoadPosition(count, position);
        if (iOnNextPageListener != null && (count - position == 2)) {
            iOnNextPageListener.onNextPage(count, ++page, position);
            nextWaiting = true;
        }
    }
}
