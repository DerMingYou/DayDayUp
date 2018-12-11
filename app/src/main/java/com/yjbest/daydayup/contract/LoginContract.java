package com.yjbest.daydayup.contract;

import com.yjbest.daydayup.base.BasePresenter;
import com.yjbest.daydayup.base.BaseView;
import com.yjbest.daydayup.http.bean.LoginResult;

/**
 * Description:
 * Data：2018/10/27-14:01
 * Author: DerMing_You
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void loginResult(LoginResult result);
    }

    interface Presenter extends BasePresenter{

        void login(String reqBody);
    }

}
