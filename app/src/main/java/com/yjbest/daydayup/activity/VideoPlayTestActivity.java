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
 * Description:
 * Data：2019/1/24-11:44
 * Author: DerMing_You
 */
public class VideoPlayTestActivity extends BaseActivity {

    public static void launch(Context context) {
        Intent intent = new Intent(context, VideoPlayTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play_test;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        JzvdStd jzvdStd = findViewById(R.id.videoplayer);
        jzvdStd.setUp("http://v.zbjsaas.com/customerTrans/30cf43ca47be2f3fa6bc4a5a61473c1b/53a9e05f-1687e8002de-0006-b924-190-c54ce.mp4"
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
