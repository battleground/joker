package com.abooc.joker.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.abooc.joker.adapter.recyclerview.ViewHolder.OnRecyclerItemClickListener;

/**
 * 加载更多RecyclerView基本
 * @author zhangjunpu
 * @date 15/5/5
 */
public class LoadMoreRecyclerManager {

    /**
     * 加载更多
     */
    public interface OnLoadMoreListener {
        /**
         * 当列表最后一项显示且isRefreshing=false的时候调用
         */
        void onLoadMore();
    }

    private Context mContext;

    private OnLoadMoreListener mOnLoadMoreListener;
    private LoadMoreRecyclerAdapter mAdapter;

    private boolean isRefreshing = false;
    private boolean isNoMore = false;
    private boolean isFailureStatus = false;

    public LoadMoreRecyclerManager(Context context, RecyclerView recyclerView) {
        mContext = context;
        mAdapter = (LoadMoreRecyclerAdapter) recyclerView.getAdapter();
        mAdapter.addOnFooterItemClickListener(new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
                isFailureStatus = false;
                if (mOnLoadMoreListener != null) {
                    mOnLoadMoreListener.onLoadMore();
                }
            }
        });
        recyclerView.addOnScrollListener(onScrollListener);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!isNoMore && !isRefreshing && !isFailureStatus) {
                LayoutManager lm = recyclerView.getLayoutManager();
                int totleCount = lm.getItemCount();
                int lastVisibleItem = 0;
                if (lm instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) lm;
                    lastVisibleItem = manager.findLastVisibleItemPosition();
                } else if (lm instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) lm;
                    int[] into = manager.findLastVisibleItemPositions(null);
                    int spanCount = manager.getSpanCount();
                    for (int i = 0; i < spanCount; i++) {
                        if (i < spanCount - 1) {
                            lastVisibleItem = Math.max(into[i], into[i + 1]);
                        }
                    }
                }
                boolean mLastItemVisible = (totleCount > 0) && (lastVisibleItem == totleCount - 1);
                if (mLastItemVisible) {
                    isRefreshing = true;
                    if (mOnLoadMoreListener != null) {
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        }
    };

    public void setNoMore(boolean flag) {
        isNoMore = flag;
        if (mAdapter != null) {
            mAdapter.setNoMore(flag);
        }
        // 解决断网状态下进入列表，打开网络重试后，此参数不变bug
        if (!flag) {
            isFailureStatus = false;
        }
    }

    public boolean isNoMore() {
        return isNoMore;
    }

    public void setFailureStatus(String error) {
        isFailureStatus = true;
        if (mAdapter != null) {
            mAdapter.setFailureStatus(error);
        }
    }

    /**
     * 结束本次当前加载
     */
    public final void onRefreshComplete() {
        if (isRefreshing()) {
            isRefreshing = false;
        }
    }

    /**
     * 是否正在加载中
     */
    public boolean isRefreshing() {
        return isRefreshing;
    }
}
