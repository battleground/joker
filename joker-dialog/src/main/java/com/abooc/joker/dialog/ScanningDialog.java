package com.abooc.joker.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.abooc.upnp.model.DeviceDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dayu on 2016/12/22.
 */

public class ScanningDialog extends android.app.Dialog implements AdapterView.OnItemClickListener, View.OnClickListener, ScanViewer {

    public interface OnSelectedDeviceListener {
        void onSelectedDevice(DeviceDisplay device);
    }

    private ImageView mIconView;
    private TextView mTitleText;
    private View mGuideView;
    private ListView mListView;

    private Adapter mAdapter;
    private OnSelectedDeviceListener mOnSelectedDeviceListener;

    public ScanningDialog(Context context) {
        this(context, 0);
    }

    public ScanningDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public ScanningDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    private void init() {
        setContentView(R.layout.joker_dialog_scanning);
        setCanceledOnTouchOutside(false);

        final Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        findViewById(R.id.close).setOnClickListener(this);

        mIconView = (ImageView) findViewById(R.id.icon);
        mTitleText = (TextView) findViewById(R.id.title);
        mGuideView = findViewById(R.id.guide);
        mListView = (ListView) findViewById(R.id.listView);


        mAdapter = new Adapter(getContext(), 0, new ArrayList<DeviceDisplay>());
        mAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
    }

    public void setOnSelectedDeviceListener(OnSelectedDeviceListener onSelectedDeviceListener) {
        this.mOnSelectedDeviceListener = onSelectedDeviceListener;
    }

    String iMessage;

    public void setMessage(String message) {
        this.iMessage = message;
    }

    String error;

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TAG", "position:" + position);

        if (mOnSelectedDeviceListener != null) {
            DeviceDisplay device = mAdapter.getItem(position);
            mOnSelectedDeviceListener.onSelectedDevice(device);
        }
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    @Override
    public void onScanning() {
        mIconView.setImageResource(R.drawable.ic_dialog_scanning);
        mTitleText.setText(R.string.joker_dialog_scanning_scan);

        mGuideView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        mIconView.setImageResource(R.drawable.ic_dialog_scanning_error);
        if (TextUtils.isEmpty(error)) {
            mTitleText.setText(R.string.joker_dialog_scanning_error);
        } else {
            mTitleText.setText(error);
        }

        mGuideView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void showListView() {
        mIconView.setImageResource(R.drawable.ic_dialog_scanning_ok);
        if (TextUtils.isEmpty(iMessage)) {
            mTitleText.setText(R.string.joker_dialog_scanning_selection);
        } else {
            mTitleText.setText(iMessage);
        }

        mGuideView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowList(List<DeviceDisplay> list) {
        mAdapter.addAll(list);
        mAdapter.setNotifyOnChange(true);
    }

    @Override
    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isScanning() {
        return mListView.getVisibility() == View.GONE;
    }

    @Override
    public boolean isEmpty() {
        return mAdapter == null || mAdapter.isEmpty();
    }

    @Override
    public void deviceAdded(DeviceDisplay device) {
        mAdapter.add(device);
        mAdapter.setNotifyOnChange(true);
        mAdapter.sort(UPnPScan.mComparatorByIP);
    }

    @Override
    public void deviceRemoved(DeviceDisplay device) {
        mAdapter.setNotifyOnChange(true);
        mAdapter.remove(device);
    }

    private class Adapter extends ArrayAdapter<DeviceDisplay> implements View.OnClickListener {

        AdapterView.OnItemClickListener onItemClickListener;

        public Adapter(Context context, int resource, List<DeviceDisplay> devices) {
            super(context, resource, devices);
        }

        public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
            onItemClickListener = listener;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.joker_dialog_scanning_list_item, null);
                holder = new ViewHolder(convertView, this);
                convertView.setTag(holder);
            }
            holder = (ViewHolder) convertView.getTag();

            DeviceDisplay item = getItem(position);

            holder.setPosition(position);
            holder.attachData(item);
            return convertView;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, v.getRootView(), (Integer) v.getTag(), 0);
            }
        }

        class ViewHolder {

            TextView iNameText;
            int position;
            TextView joinView;

            ViewHolder(View convertView, View.OnClickListener listener) {
                iNameText = (TextView) convertView.findViewById(R.id.name);
                joinView = (TextView) convertView.findViewById(R.id.join);
                joinView.setOnClickListener(listener);
            }

            void setPosition(int position) {
                this.position = position;
                joinView.setTag(position);
            }

            void attachData(DeviceDisplay device) {
                String name = device.getDevice().getFriendlyName();
                iNameText.setText(name);
                joinView.setText(device.isChecked() ? "已连接" : "立即加入");
            }
        }
    }

}
