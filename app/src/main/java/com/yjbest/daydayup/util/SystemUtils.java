package com.yjbest.daydayup.util;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.yjbest.daydayup.MyApplication;

import java.util.List;

/**
 * Description:
 * Data：2018/10/27-12:03
 * Author: DerMing_You
 */
public class SystemUtils {

    private static final String TAG = SystemUtils.class.getSimpleName();

    /**
     * 判断应用是否运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAppAlive(Context context, String packageName) {
        boolean isAppRunning = false;

        if (TextUtils.isEmpty(packageName)) {
            throw new IllegalArgumentException("packageName shouldn't be empty");
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // API 21废弃了，获取不了别的应用的信息，但能获取到自己的
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equalsIgnoreCase(packageName) &&
                    info.baseActivity.getPackageName().equalsIgnoreCase(packageName)) {
                isAppRunning = true;
                break;
            }
        }
        return isAppRunning;
    }

    /**
     * 判断应用是否在屏幕最前端
     * 获取到当前所有的进程,然后去遍历每一个进程,如果进程的包名和当前的包名一样且importance这个值为 100,那么app处于前台进程
     *
     * @param context
     * @return
     */
    public static boolean isRunningForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(context.getApplicationInfo().taskAffinity)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取设备ID（DeviceId会变，如刷机）
     * 可能返回空值，如平板电脑、电视等设备获取不了，如用户不拒绝权限，如一些厂商定制的系统有bug会返回都是“0”或都是“*”
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context context) {
        String deviceId;
        context = MyApplication.getInstance().getApplicationContext();
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (tm != null && tm.getDeviceId() != null) {
                deviceId = tm.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }

        } catch (RuntimeException e) {
            deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * 获取应用程序版本（versionName : 1.0.0）
     *
     * @return
     */
    public static String getVersionName() {
        PackageManager manager = MyApplication.getInstance().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.loge(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
            return "";
        }

        return info.versionName;
    }
}
