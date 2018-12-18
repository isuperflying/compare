package com.yc.compare.api;

import com.yc.compare.bean.CollectInfoRet;

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
}
