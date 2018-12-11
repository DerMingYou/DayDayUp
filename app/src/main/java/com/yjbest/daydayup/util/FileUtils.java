package com.yjbest.daydayup.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * Description: 文件操作类
 * Data：2018/10/27-11:50
 * Author: DerMing_You
 */
public class FileUtils {
    //存储文件夹
    public static final String APP_FOLDER = "DDU";
    public static final String ZBJ_IMAGES_FOLDER_NAME = "ddu_images";
    public static final String ZBJ_EXPORT_FOLDER_NAME = "ddu_export";
    public static final String ZBJ_MUSIC_NAME = "ddu_video";//存放视频及音频
    public static final String ZBJ_LOG_FOLDER_NAME = "ddu_log";

    /**
     * 判断SD卡是否存在
     *
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)){
            return true;
        } else {
            return false;
        }
    }

    public static File createTmpFile(Context context) {
        if (isExternalStorageWritable()) {
            // 已挂载
            File pic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            pic.mkdirs();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(pic, fileName + ".jpg");
            return tmpFile;
        } else {
            File cacheDir = context.getCacheDir();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
            String fileName = "multi_image_" + timeStamp + "";
            File tmpFile = new File(cacheDir, fileName + ".jpg");
            return tmpFile;
        }
    }

    public static File getExternalStorage(final String folder, String fileName) {
        File file = null;
        if (isExternalStorageWritable()) {
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + folder + File.separator + fileName);
        }
        if (file != null) {
            makeDirs(file.getPath());
        }
        return file;
    }

    public static File getInternalStorage(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        return file;
    }

    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosition = filePath.lastIndexOf(File.separator);
        return (filePosition == -1) ? "" : filePath.substring(0, filePosition);
    }

    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }


    /**
     * 获取保存路径
     */
    public static synchronized File savePath() {

        String imageName = DateFormat.format("yyyyMMdd_hhhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        if (FileUtils.isExternalStorageWritable()) {
            return FileUtils.getExternalStorage(APP_FOLDER, ZBJ_IMAGES_FOLDER_NAME + File.separator + imageName);
        }
        return null;
    }

    /**
     * 获取zip保存路径
     */
    public static synchronized File saveZIPPath(String name) {

        String zipName = DateFormat.format("yyyyMMdd_hhhmmss", Calendar.getInstance(Locale.CHINA)) + ".zip";

        if (!TextUtils.isEmpty(name)) {
            zipName = name + ".zip";
        }

        if (FileUtils.isExternalStorageWritable()) {
            return FileUtils.getExternalStorage(APP_FOLDER, ZBJ_EXPORT_FOLDER_NAME + File.separator + zipName);
        }
        return null;
    }

    /**
     * 获取MP3保存路径
     */
    public static synchronized File saveMP3Path(String name) {

        String zipName = DateFormat.format("yyyyMMdd_hhhmmss", Calendar.getInstance(Locale.CHINA)) + ".mp3";

        if (!TextUtils.isEmpty(name)) {
            zipName = name + ".mp3";
        }

        if (FileUtils.isExternalStorageWritable()) {
            return FileUtils.getExternalStorage(APP_FOLDER, ZBJ_MUSIC_NAME + File.separator + zipName);
        }
        return null;
    }

    /**
     * 获取MP4保存路径
     */
    public static synchronized File saveVideoPath(String name) {

        String zipName = DateFormat.format("yyyyMMdd_hhhmmss", Calendar.getInstance(Locale.CHINA)) + ".mp4";

        if (!TextUtils.isEmpty(name)) {
            zipName = name + ".mp4";
        }

        if (FileUtils.isExternalStorageWritable()) {
            return FileUtils.getExternalStorage(APP_FOLDER, ZBJ_MUSIC_NAME + File.separator + zipName);
        }
        return null;
    }

    /**
     * 获取保存路径(保存成png格式)
     */
    public static synchronized File savePathPng() {

        String imageName = DateFormat.format("yyyyMMdd_hhhmmss", Calendar.getInstance(Locale.CHINA)) + ".png";
        if (FileUtils.isExternalStorageWritable()) {
            return FileUtils.getExternalStorage(APP_FOLDER, ZBJ_IMAGES_FOLDER_NAME + File.separator + imageName);
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param file 文件
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    public static String convertUriToString(Context context, Uri uri) {
        if (uri == null)
            return null;

        String path = null;

        final String scheme = uri.getScheme();

        if (scheme == null) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            path = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);

            try {
                int column = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    path = cursor.getString(column);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (cursor != null)
                        cursor.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return path;
    }


    /**
     * unity 保存路径
     *
     * @param context
     * @param jsondata
     * @return
     */
    public static String saveLocalData(Context context, String jsondata) {
        String dir = context.getExternalFilesDir("zbj") + "/data.txt";
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir), "UTF-8"));
            writer.write(jsondata);
            writer.close();
        } catch (RuntimeException e) {
            Log.e("TAG", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dir;
    }

    /**
     * unity 读取路径
     *
     * @param fileUrl
     * @return
     */
    public static String readloadFile(String fileUrl) {
        String jsondata = "";
        try {
            File file = new File(fileUrl);
            InputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader
                        = new InputStreamReader(instream, "UTF-8");
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                while ((line = buffreader.readLine()) != null) {
                    jsondata = jsondata + line;
                }
                instream.close();
            }
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
        return jsondata;
    }

    /**
     * 判断图片路径是否存在
     *
     * @param path
     * @return
     */
    public static boolean isExistPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        //        if (!file.exists()) {
        //            file.mkdir();
        //        }
        return file.exists();
    }

    /**
     * 保存日志
     *
     * @param jsondata
     * @return
     */
    public static String saveLog(String jsondata) {
        String dir = FileUtils.getExternalStorage(APP_FOLDER, ZBJ_LOG_FOLDER_NAME
                + File.separator + TimeUtils.getCurData().replace(":","-") + ".txt").getAbsolutePath();
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dir), "UTF-8"));
            writer.write(jsondata);
            writer.close();
        } catch (RuntimeException e) {
            Log.e("TAG", e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dir;
    }


    //保存zip文件到本地
    public static void writeFile2Disk(Response<ResponseBody> response, File file, HttpCallBack httpCallBack) {

        long currentLength = 0;
        OutputStream os = null;

        InputStream is = response.body().byteStream();
        long totalLength = response.body().contentLength();

        try {

            os = new FileOutputStream(file);

            int len;

            byte[] buff = new byte[1024];

            while ((len = is.read(buff)) != -1) {

                os.write(buff, 0, len);
                currentLength += len;
                httpCallBack.onLoading(currentLength, totalLength);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public interface HttpCallBack {
        void onLoading(long currentLength, long totalLength);
    }
}
