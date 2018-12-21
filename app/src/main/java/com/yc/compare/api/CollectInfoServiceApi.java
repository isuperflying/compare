package com.yc.compare.api;

import com.yc.compare.bean.AddCollectInfoRet;
import com.yc.compare.bean.CollectInfoRet;
import com.yc.compare.bean.CollectTypeRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface CollectInfoServiceApi {

    @POST("v1.user/collectList")
    Observable<CollectInfoRet> getCollectInfoList(@Body RequestBody requestBody);

    @POST("v1.goods/doCollect")
    Observable<AddCollectInfoRet> doCollect(@Body RequestBody requestBody);

    @POST("v1.user/collectOptData")
    Observable<CollectTypeRet> collectType(@Body RequestBody requestBody);

    @POST("v1.user/delCollect")
    Observable<CollectInfoRet> deleteCollect(@Body RequestBody requestBody);

    @POST("v1.user/visitList")
    Observable<CollectInfoRet> historyList(@Body RequestBody requestBody);
}
