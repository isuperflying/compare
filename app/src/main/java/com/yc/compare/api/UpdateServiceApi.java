package com.yc.compare.api;

import com.yc.compare.bean.UpdateInfoRet;
import com.yc.compare.bean.UserInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface UpdateServiceApi {

    @POST("v1.user/editHeadPic")
    Observable<UpdateInfoRet> updateHead(@Body RequestBody requestBody);

    @POST("v1.user/editNickname")
    Observable<UpdateInfoRet> updateNickName(@Body RequestBody requestBody);

    @POST("v1.user/feedback")
    Observable<UpdateInfoRet> addSuggest(@Body RequestBody requestBody);
}
