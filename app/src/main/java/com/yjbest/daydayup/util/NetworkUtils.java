package com.yjbest.daydayup.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Description:
 * Data：2018/10/27-10:41
 * Author: DerMing_You
 */
public final class NetworkUtils {

    /**
     * 判断网络连接是否可用
     *
     * @param context
     * @return
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null){
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getIpAddressString() {
        try {
            for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces();
                    enNetI.hasMoreElements();){
                NetworkInterface netI = enNetI.nextElement();
                for (Enumeration<InetAddress> enumIpAddress = netI
                        .getInetAddresses(); enumIpAddress.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddress.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * POST提交Json数据
     * 使用Request的post方法来提交请求体RequestBody
     *
     * @param criteria
     * @return
     */
    public static RequestBody getRequestBody(String criteria) {
        return RequestBody.create(MediaType.parse("application/json"), criteria);
    }
}
