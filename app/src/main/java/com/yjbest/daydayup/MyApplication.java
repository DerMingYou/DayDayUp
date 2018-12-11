package com.yjbest.daydayup;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import com.yjbest.daydayup.http.ApiClient;
import com.yjbest.daydayup.http.bean.AccessToken;
import com.yjbest.daydayup.util.LogUtils;
import com.yjbest.daydayup.util.SPUtils;
import com.yjbest.daydayup.util.SystemUtils;

/**
 * Description:
 * Data：2018/10/27-11:16
 * Author: DerMing_You
 */
public class MyApplication extends Application {

    private static MyApplication myApplication;

    public static MyApplication getInstance(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        ApiClient.init();

        myApplication = this;

        LogUtils.logInit(true);
    }

    /**
     * 这属于Model层，操作应该放到相应的presenter，但是缓存用户信息，可能在几个地方会用到，
     * 分散开不利于维护，缓存用户信息也是最基本内容，所以放到这，有更好思路，在改进
     * @param context
     * @param token
     */
    public static void saveTokenInfo(Context context, AccessToken token) {
        if (context == null || token == null || token.getData() == null)
            return;

        SPUtils.newInstance(context).putString(AppConstants.EXPIRES_IN, token.getData().getExpires_in());
        SPUtils.newInstance(context).putString(AppConstants.ACCESS_TOKEN, token.getData().getAccess_token());
        SPUtils.newInstance(context).putLong(AppConstants.LOGIN_TIME, System.currentTimeMillis());
    }

    /**
     * 获取设备型号
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public static String getUserId() {
        if (SPUtils.newInstance(myApplication.getApplicationContext()).getString(AppConstants.ID) == null) {
            return "";
        }
        return SPUtils.newInstance(myApplication.getApplicationContext()).getString(AppConstants.ID);
    }

    /**
     * @return
     */
    public static String getCompanyId() {
        if (SPUtils.newInstance(myApplication.getApplicationContext()).getString(AppConstants.COMPANY_ID) == null) {
            return "";
        }
        return SPUtils.newInstance(myApplication.getApplicationContext()).getString(AppConstants.COMPANY_ID);
    }

    public static String getPassportId() {
        if (SPUtils.newInstance(myApplication.getApplicationContext()).getString(AppConstants.PASSPORT_ID) == null) {
            return "";
        }
        return SPUtils.newInstance(myApplication.getApplicationContext()).getString(AppConstants.PASSPORT_ID);
    }

    public static String getDeviceId() {
        return SystemUtils.getDeviceId(myApplication);
    }

}
