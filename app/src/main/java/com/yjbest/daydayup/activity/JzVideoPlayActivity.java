package com.yjbest.daydayup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yjbest.daydayup.R;
import com.yjbest.daydayup.base.BaseActivity;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * Description: 饺子视频播放
 * Data：2019/1/24-11:44
 * Author: DerMing_You
 */
public class JzVideoPlayActivity extends BaseActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, JzVideoPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jz_video_play;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        JzvdStd jzvdStd = findViewById(R.id.videoplayer);
        jzvdStd.setUp("http://v.zbjsaas.com/3cf1b1f8e5ba47a9b3fb3ae7e9b1101d/29a219e7ef2846dab89a53ef662b4aad-c44eb512c946230e675bcbacc729a7f2-ld.mp4"
                , "test", Jzvd.SCREEN_WINDOW_NORMAL);
//        jzvdStd.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    @Override
    public void initData() {

    }

    @Override
    public void showNoNetworkLayout(String msg) {

    }
}
