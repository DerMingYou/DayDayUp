package com.yjbest.daydayup.http.service;

import com.yjbest.daydayup.http.bean.AccessToken;
import com.yjbest.daydayup.http.bean.LoginResult;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Description: Retrofit访问的接口
 * Data：2018/10/26-16:31
 * Author: DerMing_You
 */
public interface RequestService {

    /**
     * 登录
     */
    @POST("user/login")
    Observable<LoginResult> login(@Body RequestBody requestBody
    );

    /**
     * 获取授权
     *
     * 使用 POST 方式时需要注意两点
     * 必须加上 @FormUrlEncoded标签，否则会抛异常
     * 必须要有参数，否则会抛异常
     */
    @FormUrlEncoded
    @POST("user/get-token")
    Call<AccessToken> getTokenSynch(
            @Body RequestBody requestBody
    );
}
