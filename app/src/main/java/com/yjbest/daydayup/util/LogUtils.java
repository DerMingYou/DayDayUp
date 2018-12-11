package com.yjbest.daydayup.util;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yjbest.daydayup.AppConstants;


/**
 * Description: Logger日志输出工具类
 * Data：2018/10/27-11:23
 * Author: DerMing_You
 */
public class LogUtils {

    public static boolean DEBUG_ENABLE = true;// 是否调试模式

    /**
     * 在application调用初始化
     *
     * @param debug
     */
    public static void logInit(boolean debug) {
        DEBUG_ENABLE = debug;
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder().
                showThreadInfo(false) // 是否显示线程信息，默认为ture
                .methodCount(2)       // 显示的方法行数，默认为2
                .methodOffset(7)      // 隐藏内部方法调用到偏移量，默认为5  .logStrategy(customLog) // 更改要打印的日志策略。
                .tag("My_Person_Tag") // 每个日志的全局标记。默认PRETTY_LOGGER
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

    public static void logd(String tag, String message) {
        if (DEBUG_ENABLE) {
            Logger.d(tag, message);
        }
    }

    public static void logd(String message) {
        if (DEBUG_ENABLE) {
            Logger.d(message);
        }
    }

    public static void loge(Throwable throwable, String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(throwable, message, args);
        }
    }

    public static void loge(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.e(message, args);
        }
    }

    public static void logi(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.i(message, args);
        }
    }

    public static void logv(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logw(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.v(message, args);
        }
    }

    public static void logwtf(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Logger.wtf(message, args);
        }
    }

    public static void logjson(String message) {
        if (DEBUG_ENABLE) {
            Logger.json(message);
        }
    }

    public static void logxml(String message) {
        if (DEBUG_ENABLE) {
            Logger.xml(message);
        }
    }

}
