package com.founq.sdk.materialtest;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton mButton;
    private LocalBroadcastManager mManager;
    private MyReceiver mReceiver;
    private MyReceiverSecond mReceiverSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mButton = findViewById(R.id.fab);
        mManager = LocalBroadcastManager.getInstance(this);
        mReceiver = new MyReceiver();
        mReceiverSecond = new MyReceiverSecond();
        IntentFilter intentFilter = new IntentFilter("action");
        mManager.registerReceiver(mReceiverSecond, intentFilter);
        mManager.registerReceiver(mReceiver, intentFilter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("action");
                intent.putExtra("data", "data");
                mManager.sendBroadcast(intent);
            }
        });
//        UIUtils.fullScreen(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_navigation:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.unregisterReceiver(mReceiver);
    }
}
