package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CategoryWrapperModel<T> {
    void getCategoryInfoList(int level,String pid, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void getCategoryWrapperList(int level, String pid, String type ,IBaseRequestCallBack<T> iBaseRequestCallBack);
}
