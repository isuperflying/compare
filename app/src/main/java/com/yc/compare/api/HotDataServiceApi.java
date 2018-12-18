package com.yc.compare.api;

import com.yc.compare.bean.HotDataInfoRet;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by iflying on 2018/2/6.
 */

public interface HotDataServiceApi {

    @GET("subject?pageSize=12&keyword=1&t=2")
    Observable<HotDataInfoRet> getDataByPage(@Query("pageNum") String page);
}
