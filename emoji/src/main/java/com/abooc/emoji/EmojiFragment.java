package com.abooc.emoji;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import com.abooc.util.Debug;

import java.util.ArrayList;


/**
 * 表情
 */
public class EmojiFragment extends Fragment {

    ViewPager viewPager;

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

        viewPager = (ViewPager) view.findViewById(R.id.ViewPager);

        PagerAdapter pagerAdapter = new ViewAdapter(getContext());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    PagerAdapter buildFragmentAdapter() {
        FragmentManager manager = getChildFragmentManager();
        FragmentHandlerAdapter tabsAdapter = new FragmentHandlerAdapter(manager, getContext());
        tabsAdapter.addTab(new FragmentHandlerAdapter.TabInfo(GiftsFragment.class, "Gifts", null))
                .addTab(new FragmentHandlerAdapter.TabInfo(EmojiAddFragment.class, "Emoji-Add", null));
        viewPager.setOffscreenPageLimit(tabsAdapter.getCount());
        return tabsAdapter;
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
            EmojiAdapter adapter = new EmojiAdapter(mContext, R.layout.emoji_item, strings);


            LayoutInflater inflater = LayoutInflater.from(mContext);
            GridView gridView = (GridView) inflater.inflate(R.layout.emoji, container, false);
            gridView.setTag(position);
            gridView.setOnItemClickListener(mOnItemClickListener);
            gridView.setAdapter(adapter);

            container.addView(gridView);
            return gridView;
        }

        OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int pageIndex = (int) parent.getTag();
                String[] strings = emojis.get(pageIndex);

                if (position == (strings.length - 1)) {
                    Debug.anchor("退格键：" + position);
                } else {
                    Debug.anchor("表情：" + position);
                }
            }
        };

        static ArrayList<String[]> emojis = new ArrayList<>();

        static {
            String[] strings0 = {
                    "第一项", "第一项", "第一项", "第一项", "第一项",
                    "第2项", "第2项", "第2项", "第2项", "第2项",
                    "第3项", "第3项", "第3项", "第3项", "第3项",
                    "第4项", "第4项", "第4项", "第4项", "第4项",
                    "退格键"
            };
            String[] strings1 = {
                    "第一项", "第一项", "第一项", "第一项", "第一项",
                    "第2项", "第2项", "第2项", "第2项", "第2项",
                    "第4项", "第4项", "第4项", "第4项", "第4项",
                    "退格键"
            };
            String[] strings2 = {
                    "第一项", "第一项", "第一项", "第一项", "第一项",
                    "第4项", "第4项", "第4项", "第4项", "第4项",
                    "退格键"
            };
            emojis.add(strings0);
            emojis.add(strings1);
            emojis.add(strings2);
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


}
