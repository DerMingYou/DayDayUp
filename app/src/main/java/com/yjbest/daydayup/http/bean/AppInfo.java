package com.yjbest.daydayup.http.bean;

import android.graphics.drawable.Drawable;

/**
 * Description:
 * Data：2019/2/18-13:58
 * Author: DerMing_You
 */
public class AppInfo {
    // 包名、icon、应用名、版本号
    private String appName;
    private Drawable appIcon;
    private String packageName;
    private int versionCode;
    private String versionName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
