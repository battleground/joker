package com.abooc.joker.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abooc.upnp.model.DeviceDisplay;

import java.util.List;

/**
 * Created by dayu on 2016/12/22.
 */

public class ScanningDialog extends android.app.Dialog implements AdapterView.OnItemClickListener, View.OnClickListener, ScanViewer {

    public interface OnSelectedDeviceListener {
        void onSelectedDevice(DeviceDisplay device);
    }

    private ProgressBar mProgressBar;
    private ImageView mIconView;
    private TextView mTitleText;
    private TextView mGuideView;
    private ListView mListView;

    private ScanAdapter mScanAdapter;
    private OnSelectedDeviceListener mOnSelectedDeviceListener;

    private boolean hideButton;

    public ScanningDialog(Context context) {
        this(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
    }

    public ScanningDialog(Context context, boolean hideButton) {
        super(context, android.R.style.Theme_Holo_Dialog_NoActionBar);
        this.hideButton = hideButton;
        init();
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
        WindowManager.LayoutParams lp = window.getAttributes();
        window.getDecorView().setPadding(0, 0, 0, 0);

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        findViewById(R.id.dismiss).setOnClickListener(this);

        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mIconView = (ImageView) findViewById(R.id.icon);
        mIconView = (ImageView) findViewById(R.id.icon);
        mTitleText = (TextView) findViewById(R.id.title);
        mGuideView = (TextView) findViewById(R.id.guide);
        mListView = (ListView) findViewById(R.id.listView);


        mScanAdapter = new ScanAdapter(getContext(), hideButton);
        mScanAdapter.setOnItemClickListener(this);
        mListView.setAdapter(mScanAdapter);
    }

    public void setOnSelectedDeviceListener(OnSelectedDeviceListener onSelectedDeviceListener) {
        this.mOnSelectedDeviceListener = onSelectedDeviceListener;
    }

    String mAlertTitle;

    public void setAlertTitle(String alertTitle) {
        this.mAlertTitle = alertTitle;
    }

    String mGuideText;

    public void setGuideText(String guideText) {
        this.mGuideText = guideText;
        mGuideView.setText(guideText);
    }

    String error;

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("TAG", "position:" + position);

        if (mOnSelectedDeviceListener != null) {
            DeviceDisplay device = mScanAdapter.getItem(position);
            mOnSelectedDeviceListener.onSelectedDevice(device);
        }
        dismiss();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }

    @Override
    public void onScanning() {
        mIconView.setImageResource(R.drawable.ic_dialog_scanning);
        mTitleText.setText(R.string.joker_dialog_scanning_scan);
        mProgressBar.setVisibility(View.VISIBLE);

        mGuideView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    @Override
    public void onError() {
        mProgressBar.setVisibility(View.GONE);
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
        mGuideView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowList(List<DeviceDisplay> list) {
        mScanAdapter.update(list);
    }

    @Override
    public void onFinished() {
        mProgressBar.setVisibility(View.GONE);
        mIconView.setImageResource(R.drawable.ic_dialog_scanning_ok);
        if (TextUtils.isEmpty(mAlertTitle)) {
            mTitleText.setText(R.string.joker_dialog_scanning_selection);
        } else {
            mTitleText.setText(mAlertTitle);
        }
    }

    @Override
    public boolean isScanning() {
        return mListView.getVisibility() == View.GONE;
    }

    @Override
    public boolean isEmpty() {
        return mScanAdapter == null || mScanAdapter.isEmpty();
    }

    @Override
    public void deviceAdded(DeviceDisplay device) {
        mScanAdapter.add(device);
        mScanAdapter.notifyDataSetChanged();
    }

    @Override
    public void deviceRemoved(DeviceDisplay device) {
        mScanAdapter.remove(device);

        if (mScanAdapter.isEmpty()) {
            onError();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mOnSelectedDeviceListener = null;
    }
}
