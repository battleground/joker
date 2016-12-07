package com.abooc.joker;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.abooc.joker.fragment.RecyclerViewFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class BlankActivity extends AppCompatActivity {


    @Optional
    @InjectView(R.id.TextView)
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        ButterKnife.inject(this);


        RecyclerViewFragment fragment = new RecyclerViewFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.FrameLayout, fragment)
                .commit();

    }
}
