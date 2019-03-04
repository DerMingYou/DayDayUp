package com.yjbest.daydayup.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import java.util.Locale;

/**
 * Description:
 * Data：2019/3/4-17:30
 * Author: DerMing_You
 */
public class ChangeLanguageHelper {
    public static final int LANGUAGE_CHINA = 1;
    public static final int LANGUAGE_ENGLISH = 2;
    public static final int LANGUAGE_DEFAULT = 0;

    private static String country = null;

    private static String mLanguage = "";

    private static Resources mResources;

    private static String mAutoCountry;

    public static void init(Context context) {
//        mResources = context.getResources();
        initResources(context);
        int currentLanguage = 0;//AppConfiguration.getDefault().getAppLanguage()

        switch (currentLanguage) {
            case ChangeLanguageHelper.LANGUAGE_DEFAULT:
                country = context.getResources().getConfiguration().locale.getCountry();
                if ("TW".equals(country) || "HK".equals(country) || "MO".equals(country)) {
                    country = "CN";
                }
                if ("CN".equals(country)) {
                    mLanguage = "zh-CN";
                } else if ("US".equals(country)) {
                    mLanguage = "en";
                }
                break;
            case ChangeLanguageHelper.LANGUAGE_CHINA:
                country = "CN";
                mLanguage = "zh-CN";
                break;
            case ChangeLanguageHelper.LANGUAGE_ENGLISH:
                country = "US";
                mLanguage = "en";
                break;
            default:
                country = context.getResources().getConfiguration().locale.getCountry();
                if ("CN".equals(country)) {
                    mLanguage = "zh-CN";
                } else if ("US".equals(country)) {
                    mLanguage = "en";
                }
                break;
        }

        mAutoCountry = Locale.getDefault().getCountry();
    }

    /**
     * 获取当前字符串资源的内容
     */
    public static String getStringById(int id) {
        String string;
        if (mLanguage != null && !"".equals(mLanguage)) {
            string = mResources.getString(id, mLanguage);
        } else {
            string = mResources.getString(id, "");
        }

        if (string != null && string.length() > 0) {
            return string;
        }
        return "";
    }

    public static void changeLanguage(Context context, int language) {
        switch (language) {
            case LANGUAGE_CHINA:
                mLanguage = "zh-CN";
                country = "CN";
//                AppConfiguration.getDefault().setAppLanguage(LANGUAGE_CHINA);
                break;
            case LANGUAGE_ENGLISH:
                mLanguage = "en";
                country = "US";
//                AppConfiguration.getDefault().setAppLanguage(LANGUAGE_ENGLISH);
                break;
            case LANGUAGE_DEFAULT:
//                country = Locale.getDefault().getCountry();
                country = mAutoCountry;
                if ("TW".equals(country) || "HK".equals(country) || "MO".equals(country)) {
                    country = "CN";
                }
                if ("CN".equals(country)) {
                    mLanguage = "zh-CN";
                } else if ("US".equals(country)) {
                    mLanguage = "en";
                }
//                AppConfiguration.getDefault().setAppLanguage(LANGUAGE_DEFAULT);

                break;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtils.applyChange(context);
        }
    }

    public static boolean getDefaultLanguage() {
        return ("CN".equals(country));
    }

    public static void initResources(Context context) {
        mResources = context.getResources();
    }
}
