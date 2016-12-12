package com.abooc.joker.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.abooc.joker.adapter.JokerAdapter;
import com.abooc.joker.messageview.MessageView;

public class MainActivity extends AppCompatActivity {


    //    @Optional
//    @InjectView(R.id.TextView)
    TextView textView;

    //    @InjectView(R.id.ListView)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.ListView);
        MessageView mMessageView = (MessageView) findViewById(R.id.MessageView);
        listView.setEmptyView(mMessageView);

        JokerAdapter adapter = new JokerAdapter(this, R.layout.joker_list_avatar_item, 20);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listView.setAdapter(null);
            }
        });
    }
}
