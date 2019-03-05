package com.yjbest.daydayup.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;

import com.yjbest.daydayup.AppConstants;

import java.util.Locale;

/**
 * Description:
 * Data：2019/3/4-17:32
 * Author: DerMing_You
 */
public class LanguageUtils {

    public static Locale getSystemLocale(Context context) {
        return SPUtils.newInstance(context).getSystemCurrentLocal();
    }

    /**
     * 获取设置语言
     * @param context
     * @return
     */
    public static Locale getSetLanguageLocale(Context context) {

        //获取当前语言编号
        int currentLanguage = SPUtils.newInstance(context).getInt(AppConstants.DEFAULT_LANGUAGE);

        switch (currentLanguage) {
            case AppConstants.DEFAULT_LANGUAGE_AUTO:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    return Resources.getSystem().getConfiguration().getLocales().get(0);//解决了获取系统默认错误的问题
                } else {
                    return Locale.getDefault();
                }
            case AppConstants.DEFAULT_LANGUAGE_CHINA:
                return Locale.SIMPLIFIED_CHINESE;
            case AppConstants.DEFAULT_LANGUAGE_CHINA_TW:
                return Locale.TRADITIONAL_CHINESE;
            case AppConstants.DEFAULT_LANGUAGE_ENGLISH:
                return Locale.US;
            default:
                return Locale.SIMPLIFIED_CHINESE;
        }
    }

    /**
     * 保存修改的语音
     * @param context
     * @param select
     */
    public static void saveSelectLanguage(Context context, int select) {
        SPUtils.newInstance(context).putInt(AppConstants.DEFAULT_LANGUAGE, select);
        setAppLanguage(context);
    }

    /**
     * 设置 Local
     *
     * @param context
     * @return
     */
    public static Context setLocal(Context context) {
        return updateResources(context, getSetLanguageLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }

    /**
     * 设置语言类型
     *
     * @param context
     */
    public static void setAppLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        Locale locale = getSetLanguageLocale(context);
        config.locale = locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList localeList = new LocaleList(locale);
            LocaleList.setDefault(localeList);
            config.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(config);
            Locale.setDefault(locale);
        }
        resources.updateConfiguration(config, dm);
    }

    /**
     * 获取并保存当前系统语音
     *
     * @param context
     */
    public static void saveSystemCurrentLanguage(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else {
            locale = Locale.getDefault();
        }
        LogUtils.logd("locale.getLanguage()" + locale.getLanguage());
        SPUtils.newInstance(context).setSystemCurrentLocal(locale);
    }

    public static void onConfigurationChanged(Context context){
        saveSystemCurrentLanguage(context);
        setLocal(context);
        setAppLanguage(context);
    }
}
