package com.yc.compare.base;

import com.yc.compare.common.RetrofitManager;

import retrofit2.Retrofit;

/**
 * 描述：业务对象的基类
 */
public class BaseModel {

    //retrofit请求数据的管理类
    public Retrofit mRetrofit;

    public BaseModel() {
        mRetrofit = RetrofitManager.retrofit();
    }
}
