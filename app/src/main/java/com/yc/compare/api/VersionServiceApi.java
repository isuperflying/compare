package com.yc.compare.api;

import com.yc.compare.bean.VersionInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by iflying on 2018/2/6.
 */

public interface VersionServiceApi {

    @POST("v1.common/checkVersion")
    Observable<VersionInfoRet> checkVersion(@Body RequestBody requestBody);
}
