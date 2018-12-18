package com.yc.compare.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.yc.compare.ui.fragment.GoodTypeFragment;
import com.yc.compare.ui.fragment.HomeFragment;
import com.yc.compare.ui.fragment.MyFragment;
import com.yc.compare.ui.fragment.NewsFragment;

public class MyFragmentAdapter extends FragmentPagerAdapter {

    private final Fragment[] FRAGMENTS = new Fragment[]{new HomeFragment(), new GoodTypeFragment(), new NewsFragment(), new MyFragment()};

    public MyFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return FRAGMENTS[position];
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return FRAGMENTS.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }
}