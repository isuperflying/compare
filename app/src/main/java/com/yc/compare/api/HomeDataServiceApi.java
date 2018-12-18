package com.yc.compare.api;

import com.yc.compare.bean.HomeDataInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface HomeDataServiceApi {

    @POST("v1.home/initData")
    Observable<HomeDataInfoRet> initData(@Body RequestBody requestBody);

}
