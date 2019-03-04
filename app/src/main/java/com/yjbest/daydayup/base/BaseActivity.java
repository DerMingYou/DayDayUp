package com.yjbest.daydayup.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.google.common.eventbus.EventBus;
import com.yjbest.daydayup.MainActivity;
import com.yjbest.daydayup.http.eventbus.ChangeLanguageEventBus;
import com.yjbest.daydayup.util.LogUtils;
import com.yjbest.daydayup.util.RxBus;
import com.yjbest.daydayup.util.SwitchLanguageUtils;
import com.yjbest.daydayup.util.ToastUtils;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Description:
 * Data：2018/10/27-15:13
 * Author: DerMing_You
 */
public abstract class BaseActivity extends AppCompatActivity implements ICommonView {

    protected View mContentView;
    public Activity mContext;

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setBaseView(getLayoutId());

        initView(savedInstanceState, mContentView);

        RxBus.getInstance();

        initData();

//        rxBusCallback();
    }

    @SuppressLint("ResourceType")
    protected void setBaseView(@LayoutRes int layoutId) {
        if (layoutId <= 0) return;
        setContentView(mContentView = LayoutInflater.from(this).inflate(layoutId, null));
        mUnbinder = ButterKnife.bind(this);
    }

    protected void showToast(String msg) {
        ToastUtils.showShortToast(mContext, msg);
    }

    protected abstract int getLayoutId();

    public abstract void initView(final Bundle savedInstanceState, final View contentView);

    public abstract void initData();

    @Override
    public void showToastMsg(String msg) {

    }

    @Override
    public void showLoadingDialog() {

    }

    @Override
    public void hideLoadingDialog() {

    }

    /**
     * RxBus 使用
     */
    private void rxBusCallback(){
        RxBus.getInstance().toObservable().map(new Function<Object, ChangeLanguageEventBus>() {
            @Override
            public ChangeLanguageEventBus apply(Object o) throws Exception {
                return (ChangeLanguageEventBus) o;
            }
        }).subscribe(new Consumer<ChangeLanguageEventBus>() {
            @Override
            public void accept(ChangeLanguageEventBus eventMsg) throws Exception {
                if (eventMsg != null){
                    if (!TextUtils.isEmpty(eventMsg.getLanguageType())){
                        LogUtils.logd("eventMsg.getLanguageType() = " + eventMsg.getLanguageType());
                        changeAppLanguage(eventMsg.getLanguageType());
                    }
                }
            }
        });
    }

    public void changeAppLanguage(String languageType) {
        String sta = SwitchLanguageUtils.getLanguageLocal(mContext);
        // 本地语言设置
        Locale myLocale = new Locale(languageType);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    public void changeAppLanguage(Locale locale) {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        getResources().updateConfiguration(configuration, metrics);
        //重新启动Activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
