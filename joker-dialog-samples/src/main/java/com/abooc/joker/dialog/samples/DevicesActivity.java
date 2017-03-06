package com.abooc.joker.dialog.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abooc.joker.adapter.recyclerview.RecyclerViewAdapter;
import com.abooc.joker.adapter.recyclerview.ViewHolder;
import com.abooc.joker.dialog.ScanViewer;
import com.abooc.joker.dialog.UPnPScan;
import com.abooc.upnp.Discovery;
import com.abooc.upnp.DlnaManager;
import com.abooc.upnp.extra.Filter;
import com.abooc.upnp.model.DeviceDisplay;
import com.abooc.util.Debug;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.types.ServiceType;
import org.fourthline.cling.model.types.UDAServiceType;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DevicesActivity extends AppCompatActivity implements ScanViewer {

    TextView mMessageView;
    RecyclerView mRecyclerView;
    Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices);


        mMessageView = (TextView) findViewById(R.id.MessageView);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mAdapter = new Adapter();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);

        final UPnPScan iUPnPScan = new UPnPScan(this);
        iUPnPScan.setFilter(new Filter() {
            @Override
            public boolean check(final Device device) {
            final UDAServiceType iUDAServiceType = new UDAServiceType("ContentDirectory");
//                final UDAServiceType iUDAServiceType = new UDAServiceType("AVTransport");
                boolean b = device.findService(iUDAServiceType) != null;
                return b;
            }
        });
        iUPnPScan.start();

        Discovery.get().registerWiFiReceiver(this);
//        DlnaManager.getInstance().startService(this, AndroidUpnpServiceImpl.class);
        DlnaManager.getInstance().startService(this, AppAndroidUPnPService.class);
    }

    @Override
    public void onScanning() {
        mMessageView.setText("扫描中...");

        mMessageView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onError() {
        mMessageView.setText("错误");

        mMessageView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void notifyDataSetChanged() {

    }

    @Override
    public void showListView() {

        mMessageView.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowList(List<DeviceDisplay> list) {
        mAdapter.getCollection().update(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isScanning() {
        return false;
    }

    @Override
    public void deviceAdded(final DeviceDisplay device) {
        Debug.anchor(device.getDevice().getFriendlyName());

        device.setChecked(false);
        mAdapter.getCollection().add(device);
        mAdapter.notifyDataSetChanged();


    }

    @Override
    public void deviceRemoved(DeviceDisplay device) {
        Debug.anchor(device.getDevice().getFriendlyName());

        mAdapter.getCollection().remove(device);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 照例实现 RecyclerViewAdapter
     */
    class Adapter extends RecyclerViewAdapter<DeviceDisplay> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new Holder(v, null);
        }

        @Override
        public void onBindViewHolder(ViewHolder h, int position) {

            DeviceDisplay bean = getItem(position);
            Holder holder = (Holder) h;
            holder.attach(bean);
        }

    }

    /**
     * 照例实现 ViewHolder
     */
    class Holder extends ViewHolder {

        TextView textView;

        public Holder(View itemLayoutView, OnRecyclerItemClickListener listener) {
            super(itemLayoutView, listener);
        }

        public Holder(View itemLayoutView, OnRecyclerItemClickListener listener, OnRecyclerItemChildClickListener childListener) {
            super(itemLayoutView, listener, childListener);
        }

        @Override
        public void onBindedView(View itemLayoutView) {
            textView = (TextView) itemLayoutView.findViewById(android.R.id.text1);
        }

        void attach(DeviceDisplay item) {
            String title = item.isChecked() ? item.getDevice().getFriendlyName() : item.getDevice().getFriendlyName() + " 检查中...";
            textView.setText(title);
        }
    }


    @Override
    protected void onDestroy() {
        Debug.anchor();
        super.onDestroy();


        DlnaManager.getInstance().stop();
        Discovery.get().unregisterWiFiReceiver(this);
    }

}
