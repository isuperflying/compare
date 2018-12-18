package com.yc.compare.presenter;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CategoryWrapperPresenter {
    void getCategoryInfoList(int level,String pid);

    void getCategoryWrapperList(int level, String pid,String type);
}
