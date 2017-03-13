package com.abooc.emoji.chat;

import android.content.Context;
import android.content.res.Resources;
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

import com.abooc.emoji.EmojiCache;
import com.abooc.emoji.R;
import com.abooc.emoji.widget.PointIndicator;
import com.abooc.util.Debug;


/**
 * 表情
 */
public class EmojiFragment extends Fragment implements GridViewer, OnItemClickListener, AdapterView.OnItemLongClickListener {

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
        pagerAdapter.setData(EmojiCache.getEmojiArrays());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);

        PointIndicator iPointIndicator = (PointIndicator) getActivity().findViewById(R.id.PointIndicator);
        iPointIndicator.setViewPager(viewPager);
    }

    private OnItemClickListener mOnItemClickListener;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;

    @Override
    public void setOnItemClickListener(OnItemClickListener l) {
        mOnItemClickListener = l;
    }

    @Override
    public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener l) {
        mOnItemLongClickListener = l;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        return false;
    }


    class ViewAdapter extends PagerAdapter {

        Context mContext;
        Emoji[] mArray;
        int mCount;
        int mPage = 0;
        int pageSize = 20;

        ViewAdapter(Context context) {
            mContext = context;
        }

        int getPage(int count, int pageSize) {
            int page = (count + (pageSize - 1)) / pageSize;
            return page;
        }

        public void setData(Emoji[] array) {
            mArray = array;
            mCount = array.length;
            mPage = getPage(mCount, pageSize);

            Emoji emoji = array[0];
            Debug.anchor(emoji.name + " -> " + emoji.icon);
            Debug.anchor("总数：" + mCount + ", 页数：" + mPage);
        }

        public Emoji[] getGroup(int page) {
            int thisPageSize = page < (mPage - 1) ? pageSize : (mCount % pageSize);
            int start = (page * pageSize);
            int end = start + thisPageSize;

            Emoji[] newArray = new Emoji[thisPageSize];
            Debug.anchor("第" + page + "页数量：" + thisPageSize + ", start：" + start + "， end：" + end);
            System.arraycopy(mArray, start, newArray, 0, thisPageSize);
            return newArray;
        }

        @Override
        public int getCount() {
            return mPage;
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

            Emoji[] emojis = getGroup(position);
            EmojiAdapter adapter = new EmojiAdapter(mContext, R.layout.emoji_item, emojis);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            GridView gridView = (GridView) inflater.inflate(R.layout.emoji, container, false);
            gridView.setTag(position);
            gridView.setOnItemClickListener(EmojiFragment.this);
            gridView.setOnItemLongClickListener(EmojiFragment.this);
            gridView.setAdapter(adapter);

            container.addView(gridView);
            return gridView;
        }


    }

    static class EmojiAdapter extends ArrayAdapter<Emoji> {

        Resources mResources;
        String mPackageName;
        int resId;

        public EmojiAdapter(Context context, int resource, Emoji[] objects) {
            super(context, resource, objects);
            resId = resource;
            mResources = getContext().getResources();
            mPackageName = getContext().getPackageName();
        }

        @Override
        public int getCount() {
            return super.getCount() + 1;
        }

        @Nullable
        @Override
        public Emoji getItem(int position) {
            return position < super.getCount() ? super.getItem(position) : null;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(resId, parent, false);
            }
            Emoji item = getItem(position);

            ImageView imageView = (ImageView) convertView;

            if (item == null) {
                imageView.setImageResource(R.drawable.emoji_backspace);
            } else {
                int identifier = mResources.getIdentifier(item.icon, "drawable", mPackageName);
                imageView.setImageResource(identifier);
            }
            return imageView;
        }


    }

}
