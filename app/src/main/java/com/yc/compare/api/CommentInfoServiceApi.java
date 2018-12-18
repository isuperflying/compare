package com.yc.compare.api;

import com.yc.compare.bean.CommentInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface CommentInfoServiceApi {

    @POST("v1.goods/commentList")
    Observable<CommentInfoRet> getCommentInfoList(@Body RequestBody requestBody);
}
