package com.abooc.joker.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.abooc.upnp.model.DeviceDisplay;

import java.util.ArrayList;
import java.util.List;

public class ScanAdapter extends BaseAdapter {

    private Context mContext;
    private AdapterView.OnItemClickListener onItemClickListener;
    private List<DeviceDisplay> mList = new ArrayList<>();
    private boolean hideButton;

    public ScanAdapter(Context context, boolean hideButton) {
        mContext = context;
        this.hideButton = hideButton;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public DeviceDisplay getItem(int position) {
        if (position >= getCount()) return null;
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(List objects) {
        mList = objects;
        notifyDataSetChanged();
    }

    public List<DeviceDisplay> getList() {
        return mList;
    }

    public void add(DeviceDisplay device) {
        mList.add(device);
        notifyDataSetChanged();
    }

    public void remove(DeviceDisplay device) {
        mList.remove(device);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.joker_dialog_scanning_list_item, null);
            holder = new ViewHolder(convertView, hideButton);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();

        DeviceDisplay item = getItem(position);

        holder.setPosition(position);
        holder.attachData(item);
        return convertView;
    }

    private class ViewHolder implements View.OnClickListener {

        private TextView iNameText;
        private int position;
        private TextView joinView;

        ViewHolder(View convertView, boolean hideButton) {
            iNameText = (TextView) convertView.findViewById(R.id.name);
            joinView = (TextView) convertView.findViewById(R.id.join);
            if (hideButton) {
                joinView.setVisibility(View.GONE);
                convertView.setOnClickListener(this);
            } else {
                joinView.setVisibility(View.VISIBLE);
                joinView.setOnClickListener(this);
            }
        }

        void setPosition(int position) {
            this.position = position;
        }

        void attachData(DeviceDisplay device) {
            String name = toName(device);
            iNameText.setText(name);
            joinView.setText(device.isChecked() ? "已连接" : "立即加入");
        }

        private String toName(DeviceDisplay device) {
            return device.getDevice().getFriendlyName();
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, v.getRootView(), position, 0);
            }
        }
    }
}