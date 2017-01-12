package com.abooc.emoji;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

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

    class MessageAdapter extends ArrayAdapter<Message> {
        int resource;

        public MessageAdapter(Context context, int resource, int textViewResourceId, List<Message> objects) {
            super(context, resource, textViewResourceId, objects);
            this.resource = resource;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
                holder = new ViewHolder(getContext(), convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Message item = getItem(position);
            holder.attachData(item);

            return convertView;
        }

        class ViewHolder {
            Context mContext;
            TextView mTextView;
            TextView mOriginText;

            ViewHolder(Context context, View view) {
                mContext = context;
                mTextView = (TextView) view.findViewById(R.id.message);
                mOriginText = (TextView) view.findViewById(R.id.inputBarHint);
            }

            void attachData(Message message) {
                if (!message.hasBuild()) {
                    SpannableStringBuilder builder = EmojiActivity.buildMessage(mContext, message.message);
                    message.spannableMessage = builder;
                }

                mTextView.setText(message.spannableMessage);
                mOriginText.setText("数据源：" + message.message);
            }
        }
    }
}
