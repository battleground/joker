package com.abooc.joker.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abooc.joker.adapter.recyclerview.RecyclerViewAdapter;
import com.abooc.joker.adapter.recyclerview.ViewHolder;
import com.abooc.joker.samples.R;

import java.util.Arrays;
import java.util.List;

public class RecyclerViewFragment extends Fragment {

    public RecyclerViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycle_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.RecyclerView);

        String[] a = {"abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc", "abc"};
        List<String> stringList = Arrays.asList(a);


        RecyclerViewAdapter<String> adapter = new RecyclerViewAdapter<String>() {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.joker_list_item, parent, false);
                return new Holder(v, null);
            }

            @Override
            public void onBindViewHolder(ViewHolder h, int position) {

                String bean = getItem(position);
                Holder holder = (Holder) h;

            }

        };

        adapter.getCollection().update(stringList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(adapter);

    }

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
