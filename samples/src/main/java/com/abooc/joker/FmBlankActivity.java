package com.abooc.joker;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.abooc.joker.samples.R;

import com.abooc.joker.fragment.RecyclerViewFragment;

public class FmBlankActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fm_blank);

        RecyclerViewFragment fragment = new RecyclerViewFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment).commit();

    }
}
