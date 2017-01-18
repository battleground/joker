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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.abooc.emoji.R;
import com.abooc.emoji.test.Data;
import com.abooc.util.Debug;

import java.util.ArrayList;


/**
 * 表情
 */
public class EmojiFragment extends Fragment implements GridViewer, OnItemClickListener {

    public EmojiFragment() {
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
        pagerAdapter.setData(Data.emojis);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    private OnItemClickListener mOnItemClickListener;

    @Override
    public void setOnItemClickListener(OnItemClickListener l) {
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
        ArrayList<String[]> mArray;

        ViewAdapter(Context context) {
            mContext = context;
        }

        public void setData(ArrayList<String[]> array) {
            mArray = array;
        }

        public String[] getItem(int position) {
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
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            String[] strings = getItem(position);
            EmojiAdapter adapter = new EmojiAdapter(mContext, R.layout.emoji_item, strings);


            LayoutInflater inflater = LayoutInflater.from(mContext);
            GridView gridView = (GridView) inflater.inflate(R.layout.emoji, container, false);
            gridView.setTag(position);
            gridView.setOnItemClickListener(EmojiFragment.this);
            gridView.setAdapter(adapter);

            container.addView(gridView);
            return gridView;
        }


    }

    static class EmojiAdapter extends ArrayAdapter<String> {

        int resId;

        public EmojiAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            resId = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = LayoutInflater.from(getContext());
            ImageView imageView = (ImageView) inflater.inflate(resId, parent, false);

            if (position == (getCount() - 1)) {
                imageView.setImageResource(R.drawable.ic_editor_backspace);
            } else {
                imageView.setImageResource(R.drawable.ic_emoji_smile);
            }

            return imageView;
        }
    }


    OnItemClickListener mItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int pageIndex = (int) parent.getTag();
            String[] strings = Data.emojis.get(pageIndex);

            if (position == (strings.length - 1)) {
                Debug.anchor("退格键：" + position);
            } else {
                Debug.anchor("表情：" + position);
            }
        }
    };


}
