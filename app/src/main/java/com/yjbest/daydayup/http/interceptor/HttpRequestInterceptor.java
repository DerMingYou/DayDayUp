package com.yjbest.daydayup.http.interceptor;

import android.text.TextUtils;

import com.yjbest.daydayup.AppConstants;
import com.yjbest.daydayup.MyApplication;
import com.yjbest.daydayup.http.ApiClient;
import com.yjbest.daydayup.http.bean.AccessToken;
import com.yjbest.daydayup.util.AppUtils;
import com.yjbest.daydayup.util.SPUtils;
import com.yjbest.daydayup.util.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;

/**
 * Description: 网络拦截器，用于对网络请求头的总体设置
 * Data：2018/10/27-14:59
 * Author: DerMing_You
 */
public class HttpRequestInterceptor implements Interceptor {

    // auth()使用okhttp网络请求，okhttp调用intercept，会无限循环，所以要使用这个判断
    private boolean isNotAuthorized = true;

    private static final String KIND_ANDROID = "android";

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isLogin()) {
            if (isExpires() && isNotAuthorized) {
                //必须在auth()之前置于false
                isNotAuthorized = false;
                auth();
            }
        }

        Request original = chain.request();
        //添加请求参数
        HttpUrl url = original.url().newBuilder()
                .addQueryParameter("access_token", getToken())
                .build();
        //设置头信息
        Request request = original.newBuilder()
                .addHeader("kind", KIND_ANDROID)
                .addHeader("app-version", AppUtils.getVersion(MyApplication.getInstance()))
                .addHeader("device-info", SystemUtils.getDeviceModel())
                .addHeader("pid", MyApplication.getPassportId())
                .addHeader("uid", MyApplication.getUserId())
                .addHeader("cid", MyApplication.getCompanyId())
                .method(original.method(), original.body())
                .url(url)
                .build();

        return chain.proceed(request);
    }

    /**
     * 判断是否到期
     *
     * @return
     */
    private boolean isExpires() {
        String expiresInStr = SPUtils.newInstance(MyApplication.getInstance().getApplicationContext()).getString(AppConstants.EXPIRES_IN);
        long loginTime = SPUtils.newInstance(MyApplication.getInstance().getApplicationContext()).getLong(AppConstants.LOGIN_TIME);
        Long expiresIn = 0l;
        try {
            expiresIn = Long.parseLong(expiresInStr);
        } catch (Exception e) {
        }
        Long curTime = System.currentTimeMillis();
        return curTime - loginTime > expiresIn * 1000 - 10 * 60 * 1000;
    }

    /**
     * 权限验证
     */
    private void auth() {
        try {

            Call<AccessToken> call = ApiClient.requestService.getTokenSynch(requestBody(getTokenReqBody()));
            AccessToken result = call.execute().body();

            isNotAuthorized = true;

            MyApplication.saveTokenInfo(MyApplication.getInstance(), result);

        } catch (Exception e) {
            // 授权失败，下次请求时需要重新授权，所以要重置为true
            isNotAuthorized = true;
            e.printStackTrace();
        }
    }

    private String getToken() {
        return SPUtils.newInstance(MyApplication.getInstance().getApplicationContext()).getString(AppConstants.ACCESS_TOKEN);
    }

    private boolean isLogin() {
        String id = SPUtils.newInstance(MyApplication.getInstance().getApplicationContext()).getString(AppConstants.ID);
        if (TextUtils.isEmpty(id)) {
            return false;
        } else {
            return true;
        }
    }

    private RequestBody requestBody(String criteria) {
        return RequestBody.create(MediaType.parse("application/json"), criteria);
    }

    private String getTokenReqBody() {
        JSONObject reqBody = new JSONObject();
        try {
            reqBody.put("clientId", AppConstants.CLIENT_ID);
            reqBody.put("clientSecret", AppConstants.CLIENT_SECRET);
            reqBody.put("phone", SPUtils.newInstance(MyApplication.getInstance().getApplicationContext()).getString(AppConstants.PHONE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqBody.toString();
    }
}
