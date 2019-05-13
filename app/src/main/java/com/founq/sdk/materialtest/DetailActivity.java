package com.founq.sdk.materialtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
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
}
