package com.yjbest.daydayup;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yjbest.daydayup.activity.LoginActivity;
import com.yjbest.daydayup.activity.VideoPlayTestActivity;
import com.yjbest.daydayup.base.BaseActivity;
import com.yjbest.daydayup.util.NetworkUtils;
import com.yjbest.daydayup.util.SystemUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_hello_word)
    TextView tvHelloWord;
    @BindView(R.id.to_login)
    TextView toLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void initData() {

    }

    @Override
    public void showNoNetworkLayout(String msg) {

    }

    @OnClick({R.id.to_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.to_login:
                VideoPlayTestActivity.launch(mContext);
                break;
        }
    }
}
