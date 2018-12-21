package com.yc.compare.api;

import com.yc.compare.bean.GoodDetailInfoRet;
import com.yc.compare.bean.GoodInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface GoodInfoServiceApi {

    @POST("v1.home/goodsList")
    Observable<GoodInfoRet> getGoodInfoByType(@Body RequestBody requestBody);

    @POST("v1.goods/goodsList")
    Observable<GoodInfoRet> getGoodInfoByParams(@Body RequestBody requestBody);

    @POST("v1.goods/info")
    Observable<GoodDetailInfoRet> getGoodDetailInfoByParams(@Body RequestBody requestBody);

}
