package com.abooc.emoji.history;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abooc.emoji.EmojiBuilder;
import com.abooc.emoji.Message;
import com.abooc.emoji.R;
import com.abooc.emoji.test.Emoji;

import java.util.List;

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
                CharSequence builder = EmojiBuilder.toEmojiCharSequence(mContext, message.message, Emoji.emotionsBitmap);
                message.spannableMessage = builder;
            }

            mTextView.setText(message.spannableMessage);
            mOriginText.setText("数据源：" + message.message);
        }
    }
}