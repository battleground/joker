package com.abooc.joker.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;
import java.util.List;

/**
 * 模板：RecyclerViewAdapter & ViewHolder 配套使用参例
 * Created by dayu on 2016/12/23.
 */
class Samples {


    void onUse(Context context, View view) {

        String[] a = {
                "abc", "abc", "abc", "abc",
                "abc", "abc", "abc", "abc",
                "abc", "abc", "abc", "abc"
        };
        List<String> stringList = Arrays.asList(a);

        Adapter adapter = new Adapter();

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(android.R.id.button1);
        adapter.getCollection().update(stringList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);
    }

    /**
     * 照例实现 RecyclerViewAdapter
     */
    class Adapter extends RecyclerViewAdapter<String> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.activity_list_item, parent, false);
            return new Holder(v, null);
        }

        @Override
        public void onBindViewHolder(ViewHolder h, int position) {

            String bean = getItem(position);
            Holder holder = (Holder) h;

        }

    }

    /**
     * 照例实现 ViewHolder
     */
    class Holder extends ViewHolder {

        public Holder(View itemLayoutView, OnRecyclerItemClickListener listener) {
            super(itemLayoutView, listener);
        }

        public Holder(View itemLayoutView, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
            super(itemLayoutView, listener, childListener);
        }

        @Override
        public void onBindedView(View itemLayoutView) {

        }
    }
}
