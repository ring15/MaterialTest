package com.founq.sdk.materialtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private RecyclerView mView;
    private SwipeRefreshLayout mRefresh;
    private TextView mTitle;

    private MyAdapter mAdapter;
    private Receive mReceive;
    private LocalBroadcastManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.navigationview);
        mFab = findViewById(R.id.fab);
        mView = findViewById(R.id.rv_cardview);
        mRefresh = findViewById(R.id.refresh);
        mTitle = findViewById(R.id.title);
        mReceive = new Receive(this);
        mManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter("action1");
        mManager.registerReceiver(mReceive, intentFilter);
//        UIUtils.fullScreen(this);
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new TestBean("post"));
        init();
    }

    private void init() {
        SureEvent event = EventBus.getDefault().getStickyEvent(SureEvent.class);
        if (event != null) {
            mTitle.setText(event.isSure() ? "y" : "n");
            EventBus.getDefault().removeStickyEvent(event);
        }
        mAdapter = new MyAdapter(this);
        RecyclerView.LayoutManager manager = new GridLayoutManager(this, 2);
        mView.setLayoutManager(manager);
        mView.setAdapter(mAdapter);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRefresh.setRefreshing(false);
                            }
                        });
                    }
                }).start();
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar不是用来代替toast的，他们有不同的应用场景，Snackbar主要有一个交互功能
                Snackbar.make(v, "data delete", Snackbar.LENGTH_SHORT)
                        .setAction("undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "undo", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.item_message:
                        Toast.makeText(MainActivity.this, "message", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_address:
                        Toast.makeText(MainActivity.this, "address", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_service:
                        Toast.makeText(MainActivity.this, "service", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_setting:
                        Toast.makeText(MainActivity.this, "setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.item_suggestion:
                        Toast.makeText(MainActivity.this, "suggestion", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_navigation:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    private static class Receive extends BroadcastReceiver {

        private WeakReference<MainActivity> mReference;

        public Receive(MainActivity activity) {
            mReference = new WeakReference<>(activity);
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            mReference.get().mTitle.setText("change");
        }
    }


    //主线程
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onRefresh(SureEvent event) {
        mTitle.setText("change");
    }


    //主线程
    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onRefreshT(SureEvent event) {
        mTitle.setText("false");
        EventBus.getDefault().cancelEventDelivery(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
