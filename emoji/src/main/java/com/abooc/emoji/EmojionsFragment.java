package com.abooc.emoji;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abooc.joker.tab.Tab;
import com.abooc.joker.tab.TabManager;

import static com.abooc.emoji.EmojionsFragment.Tabs.EMOJICON;
import static com.abooc.emoji.EmojionsFragment.Tabs.GIFTS;
import static com.abooc.emoji.EmojionsFragment.Tabs.OTHERS;

public class EmojionsFragment extends Fragment implements View.OnClickListener {


    TabManager iTabManager;

    public EmojionsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emojions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        iTabManager = new TabManager(getContext(), getChildFragmentManager(), R.id.ChildTabContent);
        iTabManager.setOnSwitchListener(onSwitchListener);
        iTabManager
                .add(iTabManager.build(EMOJICON.name, EMOJICON.cls))
                .add(iTabManager.build(GIFTS.name, GIFTS.cls))
                .add(iTabManager.build(OTHERS.name, OTHERS.cls));

        Fragment fragment = iTabManager.instance(iTabManager.getTabs().get(0));
        iTabManager.switchTo(null, fragment);

        view.findViewById(R.id.emojicons_menu_emojicon).setOnClickListener(this);
        view.findViewById(R.id.emojicons_menu_gifts).setOnClickListener(this);
        view.findViewById(R.id.emojicons_menu_add).setOnClickListener(this);
    }

    private TabManager.OnSwitchListener onSwitchListener = new TabManager.OnSwitchListener() {
        @Override
        public void onSwitched(Fragment from, Fragment to) {
            Log.d("Debug", to.getTag());
        }
    };

    @Override
    public void onClick(View v) {
        onClickTab(v);
    }

    enum Tabs {
        EMOJICON("表情", EmojiFragment.class),
        GIFTS("礼物", GiftsFragment.class),
        OTHERS("添加表情", EmojiAddFragment.class);

        String name;
        Class<? extends Fragment> cls;

        Tabs(String name, Class<? extends Fragment> cls) {
            this.name = name;
            this.cls = cls;
        }
    }

    public void onClickTab(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.emojicons_menu_emojicon:
                fragment = iTabManager.instance(iTabManager.getTabs().get(0));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.emojicons_menu_gifts:
                fragment = iTabManager.instance(iTabManager.getTabs().get(1));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.emojicons_menu_add:
                Tab tab = iTabManager.getTabs().get(2);
                fragment = iTabManager.instance(tab);
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
        }
    }

}
