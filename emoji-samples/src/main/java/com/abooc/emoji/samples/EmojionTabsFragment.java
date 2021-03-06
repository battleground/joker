//package com.abooc.emoji.samples;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.abooc.emoji.chat.EmojiAddFragment;
//import com.abooc.emoji.chat.EmojiFragment;
//import com.abooc.emoji.chat.GiftsFragment;
//import com.abooc.joker.tab.TabManager;
//
//import static com.abooc.emoji.samples.EmojionTabsFragment.Tabs.EMOJICON;
//
//public class EmojionTabsFragment extends Fragment implements View.OnClickListener {
//
//
//    TabManager iTabManager;
//
//    public EmojionTabsFragment() {
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(com.abooc.emoji.R.layout.emojion_tabs, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        iTabManager = new TabManager(getContext(), getChildFragmentManager(), com.abooc.emoji.R.id.ChildTabContent);
//        iTabManager
//                .add(iTabManager.build(EMOJICON.name, EMOJICON.cls));
//
//        Fragment fragment = iTabManager.instance(iTabManager.getTabs().get(0));
//        iTabManager.switchTo(null, fragment);
//
//        view.findViewById(com.abooc.emoji.R.id.emojicons_menu_emojicon).setOnClickListener(this);
//    }
//
//
//    View mView;
//    @Override
//    public void onClick(View v) {
//        if (mView != null) {
//            mView.setSelected(false);
//        }
//        mView = v;
//        v.setSelected(true);
//
//        onClickTab(v);
//    }
//
//    enum Tabs {
//        ADD("添加表情", EmojiAddFragment.class),
//        EMOJICON("表情", EmojiFragment.class),
//        GIFTS("礼物", GiftsFragment.class);
//
//        String name;
//        Class<? extends Fragment> cls;
//
//        Tabs(String name, Class<? extends Fragment> cls) {
//            this.name = name;
//            this.cls = cls;
//        }
//    }
//
//    public void onClickTab(View view) {
//        Fragment fragment;
//        switch (view.getId()) {
////            case com.abooc.emoji.R.id.emojicons_menu_add:
////                fragment = iTabManager.instance(iTabManager.getTabs().get(0));
////                iTabManager.switchTo(iTabManager.content, fragment);
////                break;
//            case com.abooc.emoji.R.id.emojicons_menu_emojicon:
//                fragment = iTabManager.instance(iTabManager.getTabs().get(1));
//                iTabManager.switchTo(iTabManager.content, fragment);
//                break;
////            case com.abooc.emoji.R.id.emojicons_menu_gifts:
////                Tab tab = iTabManager.getTabs().get(2);
////                fragment = iTabManager.instance(tab);
////                iTabManager.switchTo(iTabManager.content, fragment);
////                break;
//        }
//    }
//
//}
