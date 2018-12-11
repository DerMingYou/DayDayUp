package com.yjbest.daydayup.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.yjbest.daydayup.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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

        initData();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
