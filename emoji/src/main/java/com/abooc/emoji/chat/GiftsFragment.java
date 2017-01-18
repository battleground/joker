package com.abooc.emoji.chat;

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

import com.abooc.emoji.R;
import com.abooc.emoji.test.Data;
import com.abooc.emoji.test.Gift;
import com.abooc.util.Debug;

import java.util.ArrayList;


/**
 * 礼物
 */
public class GiftsFragment extends Fragment implements GridViewer, AdapterView.OnItemClickListener {

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

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.ViewPager);

        ViewAdapter pagerAdapter = new ViewAdapter(getContext());
        pagerAdapter.setData(Data.gifts);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }


    private AdapterView.OnItemClickListener mOnItemClickListener;

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(parent, view, position, id);
        }
    }


    class ViewAdapter extends PagerAdapter {

        Context mContext;
        ArrayList<Gift[]> mArray;


        ViewAdapter(Context context) {
            mContext = context;
        }

        public void setData(ArrayList<Gift[]> array) {
            mArray = array;
        }

        public Gift[] getItem(int position) {
            return mArray.get(position);
        }

        @Override
        public int getCount() {
            return mArray.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Gift[] gifts = getItem(position);
            GiftAdapter adapter = new GiftAdapter(mContext, R.layout.emoji_gifts_item, gifts);


            LayoutInflater inflater = LayoutInflater.from(mContext);
            GridView gridView = (GridView) inflater.inflate(R.layout.emoji, container, false);
            gridView.setNumColumns(4);
            gridView.setTag(position);
            gridView.setOnItemClickListener(GiftsFragment.this);
            gridView.setAdapter(adapter);

            container.addView(gridView);
            return gridView;
        }

    }

    static class GiftAdapter extends ArrayAdapter<Gift> {

        int resId;

        public GiftAdapter(Context context, int resource, Gift[] objects) {
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


    AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pageIndex = (int) parent.getTag();
            Gift[] gifts = Data.gifts.get(pageIndex);

            Debug.anchor("礼物：" + position);
        }
    };

}
