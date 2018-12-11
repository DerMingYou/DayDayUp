package com.yjbest.daydayup.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

/**
 * Description:
 * Data：2018/10/27-15:03
 * Author: DerMing_You
 */
public class AppUtils {

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static String getVersion(Context context) {
        PackageManager pm = context.getPackageManager();//拿到包的管理器
        try {
            //封装了所有的功能清单中的数据
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "1.0.0";
    }

    /**
     * 获取版本code
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();//拿到包的管理器
        try {
            //封装了所有的功能清单中的数据
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 判读是否要更新
     *
     * @param curVersion
     * @param context
     * @return
     */
    public static boolean isUpdate(String curVersion, Context context) {
        try {
            String localVersion = getVersion(context);

            if (localVersion.equals(curVersion) || TextUtils.isEmpty(curVersion))
                return false;

            String[] versions = curVersion.split("\\.");
            String[] localVersions = localVersion.split("\\.");
            // 获取最小长度值 如2.1.3
            int minLen = Math.min(versions.length, localVersions.length);
            for (int i = 0; i < minLen; ) {
                if (Integer.parseInt(versions[i]) == Integer.parseInt(localVersions[i])) {
                    i++;
                } else {
                    return Integer.parseInt(versions[i]) > Integer.parseInt(localVersions[i]);
                }
            }
            return false;
        } catch (RuntimeException e) {
            Log.e("AppUtils", e.toString());
            return false;
        }
    }
}
