package com.yjbest.daydayup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yjbest.daydayup.activity.DragItemActivity;
import com.yjbest.daydayup.activity.LoginActivity;
import com.yjbest.daydayup.activity.MediaPlayActivity;
import com.yjbest.daydayup.activity.ProvinceDataActivity;
import com.yjbest.daydayup.activity.SwitchTheLanguageActivity;
import com.yjbest.daydayup.base.BaseActivity;
import com.yjbest.daydayup.util.OpenAppMarketUtils;

import butterknife.BindView;
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

    public static void reStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_hello_word, R.id.to_login, R.id.tv_select_province, R.id.tv_drag_item, R.id.tv_to_grade, R.id.tv_change_language})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hello_word:
//                JzVideoPlayActivity.launch(mContext);
                MediaPlayActivity.launch(mContext);
                break;
            case R.id.to_login:
                LoginActivity.launch(mContext);
                break;
            case R.id.tv_select_province:
                ProvinceDataActivity.launch(mContext);
                break;
            case R.id.tv_drag_item:
                DragItemActivity.launch(mContext);
                break;
            case R.id.tv_to_grade:
                OpenAppMarketUtils.bySearchOpen(mContext);
                break;
            case R.id.tv_change_language:
                SwitchTheLanguageActivity.launch(mContext);
                break;
        }
    }
}
