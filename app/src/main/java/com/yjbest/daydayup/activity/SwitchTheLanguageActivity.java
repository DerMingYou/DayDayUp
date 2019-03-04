package com.yjbest.daydayup.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.google.common.eventbus.EventBus;
import com.yjbest.daydayup.R;
import com.yjbest.daydayup.base.BaseActivity;
import com.yjbest.daydayup.http.eventbus.ChangeLanguageEventBus;
import com.yjbest.daydayup.util.OpenAppMarketUtils;
import com.yjbest.daydayup.util.RxBus;
import com.yjbest.daydayup.util.SwitchLanguageUtils;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:
 * Data：2019/3/4-14:50
 * Author: DerMing_You
 */
public class SwitchTheLanguageActivity extends BaseActivity {

    @BindView(R.id.tv_show_chinese)
    TextView tvShowChinese;
    @BindView(R.id.tv_show_chinese_traditional)
    TextView tvShowChineseTraditional;
    @BindView(R.id.tv_show_english)
    TextView tvShowEnglish;

    public static void launch(Context context) {
        Intent intent = new Intent(context, SwitchTheLanguageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_switch_the_language;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

    }

    @Override
    public void initData() {

        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        //应用用户选择语言
        configuration.locale = Locale.getDefault();
        resources.updateConfiguration(configuration, displayMetrics);

    }

    @Override
    public void showNoNetworkLayout(String msg) {

    }

    @OnClick({R.id.tv_show_chinese, R.id.tv_show_chinese_traditional, R.id.tv_show_english})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_show_chinese: //简体中文
                changeAppLanguage(Locale.SIMPLIFIED_CHINESE);
                break;
            case R.id.tv_show_chinese_traditional: //繁体中文
                changeAppLanguage(Locale.TRADITIONAL_CHINESE);
                break;
            case R.id.tv_show_english:  //英文
                changeAppLanguage(Locale.US);
                break;
        }
    }
}
