package com.yc.compare.api;

import com.yc.compare.bean.HomeDataInfoRet;
import com.yc.compare.bean.HotSearchRet;

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

    @POST("v1.common/hotSearchKey")
    Observable<HotSearchRet> hotSearch(@Body RequestBody requestBody);

}
