package com.founq.sdk.materialtest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ring on 2019/5/13.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Log.i("data","线程： " + Thread.currentThread().getName());
        Toast.makeText(context,  "线程： " + Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
    }
}
