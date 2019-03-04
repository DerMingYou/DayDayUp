package com.yjbest.daydayup.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Description: 切换手机系统语言
 * Data：2019/3/4-15:05
 * Author: DerMing_You
 */
public class SwitchLanguageUtils {

    /**
     * 设置语言
     * @param context
     * @param language
     */
    public static void setLanguageLocal(Context context, String language){
        SharedPreferences preferences;
        SharedPreferences.Editor editor;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
        editor.putString("language", language);
        editor.commit();
    }

    /**
     * 获取语言
     * @param context
     * @return
     */
    public static String getLanguageLocal(Context context){
        SharedPreferences preferences;
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = preferences.getString("language", "");
        return language;
    }
}
