package com.abooc.joker.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.abooc.joker.adapter.JokerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class MainActivity extends AppCompatActivity {


    @Optional
    @InjectView(R.id.TextView)
    TextView textView;

    @InjectView(R.id.ListView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        JokerAdapter adapter = new JokerAdapter(this, R.layout.joker_list_avatar_item, 20);
        listView.setAdapter(adapter);

    }
}
