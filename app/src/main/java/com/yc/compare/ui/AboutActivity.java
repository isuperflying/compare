package com.yc.compare.ui;

import android.os.Bundle;

import com.yc.compare.R;
import com.yc.compare.ui.base.BaseFragmentActivity;

import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class AboutActivity extends BaseFragmentActivity {


    @Override
    protected int getContextViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {

    }

    @OnClick(R.id.iv_back)
    void back() {
        popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popBackStack();
    }

}
