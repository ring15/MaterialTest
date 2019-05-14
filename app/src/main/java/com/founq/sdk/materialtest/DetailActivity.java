package com.founq.sdk.materialtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class DetailActivity extends AppCompatActivity {

    private FloatingActionButton mButton;
    private LocalBroadcastManager mManager;
    private MyReceiver mReceiver;
    private MyReceiverSecond mReceiverSecond;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mButton = findViewById(R.id.fab);
        mTextView = findViewById(R.id.test);
        mManager = LocalBroadcastManager.getInstance(this);
        mReceiver = new MyReceiver();
        mReceiverSecond = new MyReceiverSecond();
        IntentFilter intentFilter = new IntentFilter("action");
        mManager.registerReceiver(mReceiverSecond, intentFilter);
        mManager.registerReceiver(mReceiver, intentFilter);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().post(new SureEvent(true));
                new AlertDialog.Builder(DetailActivity.this)
                        .setTitle("警告")
                        .setMessage("消息")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DetailActivity.this, "确定.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(DetailActivity.this, "取消.", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
//        UIUtils.fullScreen(this);
        EventBus.getDefault().register(this);

        TestBean event = EventBus.getDefault().getStickyEvent(TestBean.class);
        if (event != null) {
            mTextView.setText(event.getString());
            EventBus.getDefault().removeStickyEvent(event);
        }
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

    @Subscribe
    public void toast(TestBean testBean){
        String string = testBean.getString();
        Toast.makeText(DetailActivity.this, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mManager.unregisterReceiver(mReceiver);
        EventBus.getDefault().unregister(this);
    }
}
