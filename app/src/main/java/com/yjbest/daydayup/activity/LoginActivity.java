package com.yjbest.daydayup.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yjbest.daydayup.MyApplication;
import com.yjbest.daydayup.R;
import com.yjbest.daydayup.base.BaseActivity;
import com.yjbest.daydayup.contract.LoginContract;
import com.yjbest.daydayup.http.bean.LoginResult;
import com.yjbest.daydayup.presenter.LoginPresenter;
import com.yjbest.daydayup.util.NetworkUtils;
import com.yjbest.daydayup.util.SystemUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description:
 * Data：2018/10/27-14:00
 * Author: DerMing_You
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    private LoginPresenter presenter;

    public static void launch(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
//        if (formType == FORM_3D){
//            ((Activity) context).overridePendingTransition(R.anim.anim_up_in, R.anim.anim_up_out);
//        } else {
//            ((Activity) context).overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
//        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        presenter = new LoginPresenter(this,this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void loginResult(LoginResult result) {

        if (result == null)
            return;
        showToast("登录成功");
        finish();
    }

    @Override
    public void showNoNetworkLayout(String msg) {

    }

    private String getReqBody(String deviceId, String deviceInfo, String ip, String password, String phone) {
        JSONObject reqBody = new JSONObject();
        try {
            reqBody.put("deviceId", deviceId);
            reqBody.put("deviceInfo", deviceInfo);
            reqBody.put("ip", ip);
            reqBody.put("password", password);
            reqBody.put("phone", phone);
            reqBody.put("source", "android");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reqBody.toString();
    }

    @OnClick({R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                presenter.login(getReqBody(MyApplication.getDeviceId(), SystemUtils.getDeviceModel(),
                        NetworkUtils.getIpAddressString(), tvPassword.getText().toString(), tvCount.getText().toString()));
                break;
        }
    }
}
