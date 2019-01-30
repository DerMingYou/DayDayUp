package com.yjbest.daydayup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ydm.mediaplay.listener.OnVideoControlListener;
import com.ydm.mediaplay.utils.DisplayUtils;
import com.ydm.mediaplay.video.BardPlayer;
import com.yjbest.daydayup.MainActivity;
import com.yjbest.daydayup.R;
import com.yjbest.daydayup.base.BaseActivity;
import com.yjbest.daydayup.http.bean.VideoBean;

import butterknife.BindView;

/**
 * Description: 视频播放
 * Data：2019/1/30-15:37
 * Author: DerMing_You
 */
public class MediaPlayActivity extends BaseActivity {

    @BindView(R.id.videoPlayer)
    BardPlayer videoPlayer;

    private String videoUrl = "http://1jbest-qatest.oss-cn-shanghai.aliyuncs.com/zhouse/app/user-guide/user-guide300.mp4";

    public static void launch(Context context) {
        Intent intent = new Intent(context, MediaPlayActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_media_play;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        videoPlayer.setOnVideoControlListener(new OnVideoControlListener() {
            @Override
            public void onStartPlay() {
                videoPlayer.startPlay();
            }

            @Override
            public void onBack() {

            }

            @Override
            public void onFullScreen() {
                DisplayUtils.toggleScreenOrientation(MediaPlayActivity.this);
            }

            @Override
            public void onRetry(int errorStatus) {

            }
        });

        videoPlayer.setPath(new VideoBean("代码人生", videoUrl));
    }

    @Override
    public void initData() {

    }

    @Override
    public void showNoNetworkLayout(String msg) {

    }
}
