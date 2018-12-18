package com.yc.compare.api;

import com.yc.compare.bean.NewsInfoDetailRet;
import com.yc.compare.bean.NewsInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface NewsInfoServiceApi {

    @POST("v1.article/articleList")
    Observable<NewsInfoRet> getNewsInfoList(@Body RequestBody requestBody);

    @POST("v1.article/articleInfo")
    Observable<NewsInfoDetailRet> getNewsDetail(@Body RequestBody requestBody);
}
