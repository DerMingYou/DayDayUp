package com.yjbest.daydayup.presenter;

import android.content.Context;

import com.yjbest.daydayup.contract.LoginContract;
import com.yjbest.daydayup.http.ApiClient;
import com.yjbest.daydayup.http.base.BaseObserver;
import com.yjbest.daydayup.http.bean.LoginResult;
import com.yjbest.daydayup.util.NetworkUtils;
import com.yjbest.daydayup.util.RxSchedulerUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Description:
 * Data：2018/10/27-14:01
 * Author: DerMing_You
 */
public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View mView;
    private Context context;

    public LoginPresenter(LoginContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
    }

    @Override
    public void login(String reqBody) {
        mView.showLoadingDialog();
        ApiClient.requestService.login(NetworkUtils.getRequestBody(reqBody))
                .compose(RxSchedulerUtils.normalSchedulersTransformer())
                .subscribe(new BaseObserver<LoginResult>(context){

                    @Override
                    public void onSuccess(LoginResult result) {
                        mView.loginResult(result);
                        mView.hideLoadingDialog();
                    }

                    @Override
                    public void onFailure(LoginResult result, int errCode, String errMsg) {
                        mView.hideLoadingDialog();
                    }
                });
    }

    @Override
    public void subscribe() {


    }

    @Override
    public void unSubscribe() {
    }
}
