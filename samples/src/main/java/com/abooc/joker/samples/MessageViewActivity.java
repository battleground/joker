package com.abooc.joker.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.abooc.joker.messageview.MessageView;
import com.abooc.util.Debug;

public class MessageViewActivity extends AppCompatActivity {

    MessageView mMessageView;
    Switch mSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Debug.debugOn();
        setContentView(R.layout.activity_messageview);

        mSwitch = (Switch) findViewById(R.id.Switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mMessageView.setRetryEnable(b);
            }
        });

        mMessageView = (MessageView) findViewById(R.id.MessageView);
        mMessageView.setRetryEnable(false);
        mMessageView.setOnRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMessageView.loading();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TextView1:
                mMessageView.loading();
                break;
            case R.id.TextView11:
                mMessageView.setProgressBarStyle();
                Debug.error();
                break;
            case R.id.TextView2:
                mMessageView.empty();
                break;
            case R.id.TextView3:
                mMessageView.noNetwork();
                mSwitch.setChecked(true);
                break;
        }
    }
}
