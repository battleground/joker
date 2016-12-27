package com.abooc.joker_tab_samples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.abooc.joker.tab.Logger;
import com.abooc.joker.tab.TabManager;

import static com.abooc.joker_tab_samples.JokerTabActivity.NAMES.ACCOUNT;
import static com.abooc.joker_tab_samples.JokerTabActivity.NAMES.CLOUD;
import static com.abooc.joker_tab_samples.JokerTabActivity.NAMES.HOME;
import static com.abooc.joker_tab_samples.JokerTabActivity.NAMES.SEARCH;

public class JokerTabActivity extends AppCompatActivity {

    TabManager iTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joker_tab);
        Logger.build(new Logger.Out() {
            @Override
            public void print(String message) {
                Log.d("TAG", message);
            }
        });
        iTabManager = new TabManager(this, getSupportFragmentManager(), R.id.TabContent);
        iTabManager.setOnSwitchListener(onSwitchListener);
        iTabManager
                .add(iTabManager.build(HOME.name, HOME.cls))
                .add(iTabManager.build(SEARCH.name, SEARCH.cls))
                .add(iTabManager.build(CLOUD.name, CLOUD.cls))
                .add(iTabManager.build(ACCOUNT.name, ACCOUNT.cls));

        Fragment fragment = iTabManager.instance(iTabManager.getTabs().get(0));
        iTabManager.switchTo(null, fragment);
    }

    private TabManager.OnSwitchListener onSwitchListener = new TabManager.OnSwitchListener() {
        @Override
        public void onSwitched(Fragment from, Fragment to) {
//            NAMES name = NAMES.valueOf(to.getTag());
//            switch (name) {
//                case HOME:
//                    setTitle(name.name);
//                    break;
//                case SEARCH:
//                    setTitle(name.name);
//                    break;
//                case CLOUD:
//                    setTitle(name.name);
//                    break;
//                case ACCOUNT:
//                    setTitle(name.name);
//                    break;
//            }

            Logger.out.print(to.getTag());
        }
    };

    enum NAMES {
        HOME("首页", HomeFragment.class),
        SEARCH("搜索", SearchFragment.class),
        CLOUD("CLOUD", CloudFragment.class),
        ACCOUNT("个人", AccountFragment.class);

        String name;
        Class<? extends Fragment> cls;

        NAMES(String name, Class<? extends Fragment> cls) {
            this.name = name;
            this.cls = cls;
        }
    }

    public void onClickTab(View view) {
        Fragment fragment;
        switch (view.getId()) {
            case R.id.menu_home:
                fragment = iTabManager.instance(iTabManager.getTabs().get(0));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.menu_search:
                fragment = iTabManager.instance(iTabManager.getTabs().get(1));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.menu_cloud:
                fragment = iTabManager.instance(iTabManager.getTabs().get(2));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
            case R.id.menu_account:
                fragment = iTabManager.instance(iTabManager.getTabs().get(3));
                iTabManager.switchTo(iTabManager.content, fragment);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (!iTabManager.goBack()) {
            super.onBackPressed();
        }
    }
}
