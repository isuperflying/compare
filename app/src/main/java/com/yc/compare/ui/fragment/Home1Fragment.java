package com.yc.compare.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.yc.compare.R;
import com.yc.compare.ui.adapter.SubFragmentAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GlideImageLoader;
import com.yc.compare.ui.fragment.sub.MainAllFragment;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class Home1Fragment extends BaseFragment {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    List<String> mTitleDataList;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home1, null);
        ButterKnife.bind(this, root);
        initViews();
        initBanner();
        return root;
    }

    public void initViews() {

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            fragments.add(MainAllFragment.newInstance(i+""));
        }

        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("全部");
        mTitleDataList.add("护肤品");
        mTitleDataList.add("化妆品");
        mTitleDataList.add("包包");
        mTitleDataList.add("手表");
        mTitleDataList.add("项链");

        SubFragmentAdapter viewPageAdapter = new SubFragmentAdapter(getChildFragmentManager(), fragments, mTitleDataList);
        mViewPager.setAdapter(viewPageAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    public void initBanner() {
        List<Integer> urls = new ArrayList<>();
        urls.add(R.mipmap.b1);
        urls.add(R.mipmap.b2);
        urls.add(R.mipmap.b3);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(urls);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
    }
}
