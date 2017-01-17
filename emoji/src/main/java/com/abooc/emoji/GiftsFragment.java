package com.abooc.emoji;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.abooc.util.Debug;

import java.util.ArrayList;


public class GiftsFragment extends Fragment {

    ViewPager viewPager;

    public GiftsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emoji, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        viewPager = (ViewPager) view.findViewById(R.id.ViewPager);

        PagerAdapter pagerAdapter = new ViewAdapter(getContext());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    static class ViewAdapter extends PagerAdapter {

        Context mContext;

        ViewAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return emojis.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            String[] strings = emojis.get(position);
            GiftAdapter adapter = new GiftAdapter(mContext, R.layout.emoji_gifts_item, strings);


            LayoutInflater inflater = LayoutInflater.from(mContext);
            GridView gridView = (GridView) inflater.inflate(R.layout.emoji, container, false);
            gridView.setNumColumns(4);
            gridView.setTag(position);
            gridView.setOnItemClickListener(mOnItemClickListener);
            gridView.setAdapter(adapter);

            container.addView(gridView);
            return gridView;
        }

        AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pageIndex = (int) parent.getTag();
                String[] strings = emojis.get(pageIndex);

                Debug.anchor("礼物：" + position);
            }
        };

        static ArrayList<String[]> emojis = new ArrayList<>();

        static {
            String[] strings0 = {
                    "第一项", "第一项", "第一项", "第一项",
                    "第3项", "第3项", "第3项"
            };
            String[] strings1 = {
                    "第一项", "第一项", "第一项", "第一项"
            };
            emojis.add(strings0);
            emojis.add(strings1);
        }

    }

    static class GiftAdapter extends ArrayAdapter<String> {

        int resId;

        public GiftAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            resId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View view = inflater.inflate(resId, parent, false);

            return view;
        }
    }
}
