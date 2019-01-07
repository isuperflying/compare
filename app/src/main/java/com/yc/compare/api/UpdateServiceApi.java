package com.yc.compare.api;

import com.yc.compare.bean.UpdateInfoRet;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface UpdateServiceApi {

    @Multipart
    @POST("v1.user/editHeadPic")
    Observable<UpdateInfoRet> updateHead(@Part("uid") RequestBody requestBody,
                                         @Part MultipartBody.Part file);

    @POST("v1.user/editNickname")
    Observable<UpdateInfoRet> updateNickName(@Body RequestBody requestBody);

    @POST("v1.user/feedback")
    Observable<UpdateInfoRet> addSuggest(@Body RequestBody requestBody);
}
