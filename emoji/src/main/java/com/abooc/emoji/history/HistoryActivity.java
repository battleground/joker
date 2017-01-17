package com.abooc.emoji.history;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;


import com.abooc.emoji.test.Data;
import com.abooc.emoji.R;

public class HistoryActivity extends AppCompatActivity {


    public static void launch(Context context) {
        Intent intent = new Intent(context, HistoryActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_history);

        Data.buildMessages();

        ListView mListView = (ListView) findViewById(R.id.message_list);
        mListView.setAdapter(new MessageAdapter(this, R.layout.message_item, R.id.message, Data.mMessageBuilder.list));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
