package com.yc.compare.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;


public class SubFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private List<String> titles;
    public SubFragmentAdapter(FragmentManager fragmentManager, List<Fragment> listFragments,List<String> titles) {
        super(fragmentManager);
        this.fragments = listFragments;
        this.titles = titles;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

}