package com.yjbest.daydayup.util;


import android.os.Environment;
import android.text.format.DateFormat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.Date;

import static com.yjbest.daydayup.util.FileUtils.APP_FOLDER;
import static com.yjbest.daydayup.util.FileUtils.ZBJ_LOG_FOLDER_NAME;

/**
 * Description:
 * Data：2018/10/27-12:02
 * Author: DerMing_You
 */
public class CrashUtils implements UncaughtExceptionHandler{

    private UncaughtExceptionHandler exceptionHandler;

    public CrashUtils() {
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        writeToFile(
                writer.toString() + "\n" + thread.getName() + "\n"
                        + thread.toString(),FileUtils.getExternalStorage(APP_FOLDER, ZBJ_LOG_FOLDER_NAME
                        + File.separator + TimeUtils.getCurData()+"z"+ ".txt").getAbsolutePath());
        exceptionHandler.uncaughtException(thread, ex);
    }

    private void writeToFile(String stacktrace, String filename) {
        try {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_UNMOUNTED)) {
                return;
            }
            File targetFile = new File(filename);

            FileWriter fileWriter = new FileWriter(targetFile);
            BufferedWriter bos = new BufferedWriter(fileWriter);
            bos.write(android.os.Build.VERSION.SDK_INT + "\n");
            bos.write(android.os.Build.MODEL + "\n");
            bos.write("APP :" + SystemUtils.getVersionName() + "\n");
            bos.write("time :"
                    + DateFormat.format("yyyy-MM-dd kk:mm:ss",
                    new Date(System.currentTimeMillis())) + "\n");
            bos.write(stacktrace);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
