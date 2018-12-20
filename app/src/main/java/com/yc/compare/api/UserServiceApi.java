package com.yc.compare.api;

import com.yc.compare.bean.UserInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface UserServiceApi {

    @POST("v1.user/login")
    Observable<UserInfoRet> userLogin(@Body RequestBody requestBody);

    @POST("v1.user/register")
    Observable<UserInfoRet> register(@Body RequestBody requestBody);

    @POST("v1.common/hotBrandList")
    Observable<UserInfoRet> sendSms(@Body RequestBody requestBody);

    @POST("addSuggest")
    Observable<UserInfoRet> addSuggest(@Body RequestBody requestBody);

    @POST("v1.user/thirdLogin")
    Observable<UserInfoRet> otherLogin(@Body RequestBody requestBody);


    @POST("v1.user/findpass")
    Observable<UserInfoRet> forget(@Body RequestBody requestBody);
}
