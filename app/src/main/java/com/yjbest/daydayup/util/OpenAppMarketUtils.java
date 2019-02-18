package com.yjbest.daydayup.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.yjbest.daydayup.R;
import com.yjbest.daydayup.http.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Data：2019/2/18-13:40
 * Author: DerMing_You
 */
public class OpenAppMarketUtils {

    /**
     * 方法一  通过系统隐式意图方式去调用应用市场app详情页
     *
     * @param context
     */
    public static void byIntentOpen(Context context){
        try{
            Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }catch(Exception e){
            Toast.makeText(context, context.getString(R.string.no_install_app_market), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 方法二  通过应用市场的搜索方法来调用app详情页
     *
     * @param context
     */
    public static void bySearchOpen(Context context){
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://search?q="+ context.getPackageName()));
            context.startActivity(i);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.no_install_app_market), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 方法三  根据手机安装的应用市场，来定制自己所需要的风格
     * @param context
     * @param appPkg
     * @param marketPkg
     */
    public void byAppDetail(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取手机上已经安装的应用市场
     * 获取有在AndroidManifest 里面注册<category android:name="android.intent.category.APP_MARKET" />的app
     * @param context
     * @return
     */
    public ArrayList<String> getInstallAppMarkets(Context context) {
        //默认的应用市场列表，有些应用市场没有设置APP_MARKET通过隐式搜索不到
        ArrayList<String>  marketList = new ArrayList<>();
        marketList.add("com.xiaomi.market");
        marketList.add("com.qihoo.appstore");
        marketList.add("com.wandoujia.phoenix2");
        marketList.add("com.tencent.android.qqdownloader");
        marketList.add("com.taptap");
        ArrayList<String> markets = new ArrayList<String>();
        if (context == null)
            return markets;
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        if (infos == null || infos.size() == 0)
            return markets;
        int size = infos.size();
        for (int i = 0; i < size; i++) {
            String pkgName = "";
            try {
                ActivityInfo activityInfo = infos.get(i).activityInfo;
                pkgName = activityInfo.packageName;


            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!TextUtils.isEmpty(pkgName))
                markets.add(pkgName);

        }
        //取两个list并集,去除重复
        marketList.removeAll(markets);
        markets.addAll(marketList);
        return markets;
    }

    private ArrayList<AppInfo> appInfos = new ArrayList<>();

    /**
     * 过滤出已经安装的包名集合
     *
     * @param context
     * @param pkgs
     * @return
     */
    public ArrayList<String> getFilterInstallMarkets(Context context,ArrayList<String> pkgs) {
        appInfos.clear();
        ArrayList<String> appList = new ArrayList<String>();
        if (context == null || pkgs == null || pkgs.size() == 0)
            return appList;
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);
        int li = installedPkgs.size();
        int lj = pkgs.size();
        for (int j = 0; j < lj; j++) {
            for (int i = 0; i < li; i++) {
                String installPkg = "";
                String checkPkg = pkgs.get(j);
                PackageInfo packageInfo = installedPkgs.get(i);
                try {
                    installPkg = packageInfo.packageName;

                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(installPkg))
                    continue;
                if (installPkg.equals(checkPkg)) {
                    // 如果非系统应用，则添加至appList,这个会过滤掉系统的应用商店，如果不需要过滤就不用这个判断
                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        //将应用相关信息缓存起来，用于自定义弹出应用列表信息相关用
                        AppInfo appInfo = new AppInfo();
                        appInfo.setAppName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
                        appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
                        appInfo.setPackageName(packageInfo.packageName);
                        appInfo.setVersionCode(packageInfo.versionCode);
                        appInfo.setVersionName(packageInfo.versionName);
                        appInfos.add(appInfo);
                        appList.add(installPkg);
                    }
                    break;
                }
            }
        }
        return appList;
    }
}
