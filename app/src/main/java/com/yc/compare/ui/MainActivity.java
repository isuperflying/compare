package com.yc.compare.ui;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.yc.compare.R;
import com.yc.compare.ui.adapter.MyFragmentAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by iflying on 2017/12/14.
 */

@RuntimePermissions
public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private final int[] TITLES = new int[]{R.string.tab_home, R.string.tab_fight, R.string.tab_show, R.string.tab_my};

    private final int[] IMAGES = new int[]{R.drawable.tab_home_selector, R.drawable.tab_fight_selector, R.drawable.tab_show_selector, R.drawable.tab_my_selector};

    private MyFragmentAdapter adapter;

    private long clickTime = 0;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        MainActivityPermissionsDispatcher.showRecordWithCheck(this);
        MainActivityPermissionsDispatcher.readPhoneStateWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRecord() {
        //ToastUtils.showLong("允许使用存储权限");
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onRecordDenied() {
        Toast.makeText(this, R.string.permission_storage_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForRecord(PermissionRequest request) {
        showRationaleDialog(R.string.permission_storage_rationale, request);
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_storage_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void readPhoneState() {
        //ToastUtils.showLong("允许使用存储权限");
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneStateDenied() {
        Toast.makeText(this, R.string.permission_readphone_denied, Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationaleForPhoneState(PermissionRequest request) {
        showRationaleDialog(R.string.permission_readphone_rationale, request);
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onReadPhoneStateNeverAskAgain() {
        Toast.makeText(this, R.string.permission_readphone_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    public void initViews() {
        setTabs(tabLayout, this.getLayoutInflater(), TITLES, IMAGES);
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public void initData() {
    }

    public void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] titles, int[] images) {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);
            TextView tabText = view.findViewById(R.id.tv_tab);
            tabText.setText(titles[i]);
            ImageView tabImage = view.findViewById(R.id.iv_tab);
            tabImage.setImageResource(images[i]);
            tabLayout.addTab(tab);
        }
    }

    private void showRationaleDialog(@StringRes int messageResId, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(messageResId)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        exit();
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            clickTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            System.exit(0);
        }
    }

}
