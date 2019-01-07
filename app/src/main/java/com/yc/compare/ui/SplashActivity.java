package com.yc.compare.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.yc.compare.R;
import com.yc.compare.ui.base.BaseFragmentActivity;

/**
 * Created by myflying on 2019/1/3.
 */
public class SplashActivity extends BaseFragmentActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000);
    }

    @Override
    protected int getContextViewId() {
        return R.layout.activity_splash;
    }
}
