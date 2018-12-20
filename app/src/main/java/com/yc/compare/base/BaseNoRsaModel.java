package com.yc.compare.base;

import com.yc.compare.common.RetrofitNoRsaManager;

import retrofit2.Retrofit;

/**
 * 描述：业务对象的基类
 */
public class BaseNoRsaModel {

    //retrofit请求数据的管理类
    public Retrofit mRetrofit;

    public BaseNoRsaModel() {
        mRetrofit = RetrofitNoRsaManager.retrofit();
    }
}
