package com.yjbest.daydayup.http;

import com.baronzhang.retrofit2.converter.FastJsonConverterFactory;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yjbest.daydayup.BuildConfig;
import com.yjbest.daydayup.http.interceptor.HttpRequestInterceptor;
import com.yjbest.daydayup.http.interceptor.LoggingInterceptor;
import com.yjbest.daydayup.http.service.RequestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Description: 统一生成接口实例的管理类
 * Data：2018/10/26-16:29
 * Author: DerMing_You
 */
public final class ApiClient {

    public static RequestService requestService;

    public static void init(){
        requestService = initRequestService(BuildConfig.BASE_URL, RequestService.class);
    }

    private static <T> T initRequestService(String baseUrl, Class<T> clazz) {

        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(new StethoInterceptor());
            builder.addInterceptor(new LoggingInterceptor());
        }
        //创建一个okhttpclient
        OkHttpClient client = builder.addInterceptor(new HttpRequestInterceptor())
                .connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS) //retryOnConnectionFailure(true);//错误重连
                .build();

        //创建一个Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(FastJsonConverterFactory.create()) //添加转换器Converter(将 json 转为 JavaBean)：
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit.create(clazz);
    }
}
