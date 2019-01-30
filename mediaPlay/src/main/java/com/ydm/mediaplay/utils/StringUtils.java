package com.ydm.mediaplay.utils;

import java.util.Locale;

/**
 * Description: 字符转化的工具类
 * Data：2019/1/30-14:55
 * Author: DerMing_You
 */
public class StringUtils {

    public static String stringForTime(int timeMs) {
        int totalSeconds = timeMs / 1000;

        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours   = totalSeconds / 3600;

        if (hours > 0) {
            return String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        }
    }
}
