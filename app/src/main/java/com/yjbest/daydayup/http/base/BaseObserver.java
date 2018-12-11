package com.yjbest.daydayup.http.base;

import android.accounts.NetworkErrorException;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ParseException;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.yjbest.daydayup.MyApplication;
import com.yjbest.daydayup.R;
import com.yjbest.daydayup.util.LogUtils;
import com.yjbest.daydayup.util.ToastUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


/**
 * Description: 观察者基类
 * Data：2018/10/26-16:33
 * Author: DerMing_You
 */
public abstract class BaseObserver<T> implements Observer<T> {

    protected Context mContext;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;

    private Disposable disposable;

    public BaseObserver(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(t == null){
            onFailure(null, 0, MyApplication.getInstance().getString(R.string.service_error));
            return;
        }
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        LogUtils.logd("");
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(ExceptionReason.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(ExceptionReason.PARSE_ERROR);
        } else {
            onException(ExceptionReason.UNKNOWN_ERROR);
        }
        onFailure(null, 0, MyApplication.getInstance().getString(R.string.service_error));
    }

    @Override
    public void onComplete() {

    }

    /**
     * 返回成功了,但是code错误
     *
     * @param t
     * @throws Exception
     */
    protected void onCodeError(T t) throws Exception { }

    public abstract void onSuccess(T result);

    public abstract void onFailure(T result, int errCode, String errMsg);

    /**
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtils.showShortToast(mContext, R.string.connect_error);
                break;

            case CONNECT_TIMEOUT:
                ToastUtils.showShortToast(mContext, R.string.connect_timeout);
                break;

            case BAD_NETWORK:
                ToastUtils.showShortToast(mContext, R.string.bad_network);
                break;

            case PARSE_ERROR:
                ToastUtils.showShortToast(mContext, R.string.parse_error);
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtils.showShortToast(mContext, R.string.unknown_error);
                break;
        }
    }

    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
