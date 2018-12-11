package com.yjbest.daydayup.http.interceptor;

import android.util.Log;

import com.yjbest.daydayup.BuildConfig;

import org.json.JSONException;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Description:
 * Data：2018/10/27-14:38
 * Author: DerMing_You
 */
public final class LoggingInterceptor implements Interceptor {

    private static String HTTP = "HTTP";
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static int LOG_MAXLENGTH = 3000;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!BuildConfig.LOG_DEBUG) {
            return chain.proceed(request);
        }

        //未加密
        StringBuilder paramsLog = new StringBuilder();
        paramsLog.append("--> " + request.method());
        paramsLog.append("  " + request.url());

        // 打印参数
        RequestBody requestBody = request.body();
        StringBuilder paramsBulid = new StringBuilder();
        if (requestBody != null) {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            paramsBulid.append("--> " + request.method());
            paramsBulid.append("  " + buffer.readString(charset));
        }

        //收集请求参数，方便调试
        RequestBody body = request.body();
        if (body != null) {
            if (body instanceof FormBody) {
                FormBody formBody = (FormBody) body;

                //添加原请求体
                for (int i = 0; i < formBody.size(); i++) {
                    if (i == 0) {
                        //未加密
                        paramsLog.append("?");
                        paramsLog.append(formBody.name(i));
                        paramsLog.append("=");
                        paramsLog.append(formBody.value(i));
                    } else {
                        //未加密
                        paramsLog.append("&");
                        paramsLog.append(formBody.name(i));
                        paramsLog.append("=");
                        paramsLog.append(formBody.value(i));
                    }
                }

            } else if (body instanceof MultipartBody) {
                MultipartBody formBody = (MultipartBody) body;
                //添加原请求体
                for (int i = 0; i < formBody.size(); i++) {
                    paramsLog.append("&");
                    paramsLog.append(formBody.part(i));
                }

            }
        }
        //请求输出
        Log.d(HTTP, paramsLog.toString());
        Log.d(HTTP, paramsBulid.toString());
        //返回结果
        Response response = chain.proceed(request);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();


        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }

        if (contentLength != 0) {
            //输出
            String result = null;
            try {
                result = decodeUnicode(buffer.clone().readString(charset));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //            Log.d("HTTP", format(result));
            //打印json日志
            show(format(result));
            //            if (result.length() > LOG_MAXLENGTH) {
            //                Log.d("HTTP", result.substring(0, LOG_MAXLENGTH));
            //                Log.d("HTTP", result.substring(LOG_MAXLENGTH, result.length()));
            //            } else {
            //                Log.d("HTTP", result);
            //            }
        }
        return response;
    }

    /**
     * 编码转换
     *
     * @param theString
     * @return
     * @throws JSONException
     */
    private static String decodeUnicode(String theString) throws JSONException {
        char aChar;
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = '\t';
                    else if (aChar == 'r')
                        aChar = '\r';
                    else if (aChar == 'n')
                        aChar = '\n';
                    else if (aChar == 'f')
                        aChar = '\f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }

        return outBuffer.toString();
    }

    /**
     * json 格式控制台输出
     *
     * @param jsonStr
     * @return
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < jsonStr.length(); i++) {
            char c = jsonStr.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }


        return jsonForMatStr.toString();

    }

    /**
     * android的logcat的message有字符长度的限制,超过将直接截断
     *
     * @param str
     */
    public static void show(String str) {
        str = str.trim();
        int index = 0;
        int maxLength = 3500;
        int step = 1;
        String sub;
        while (index < str.length()) {
            // java的字符不允许指定超过总的长度end
            if (str.length() <= index + maxLength) {
                sub = str.substring(index);
            } else {
                sub = str.substring(index, maxLength * step);
            }
            index += maxLength;
            step++;

            Log.d(HTTP, sub.trim());
        }
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }
}
