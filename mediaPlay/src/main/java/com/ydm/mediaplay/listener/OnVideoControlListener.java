package com.ydm.mediaplay.listener;

/**
 * Description:视频控制监听
 * Data：2019/1/30-15:09
 * Author: DerMing_You
 */
public interface OnVideoControlListener {
    /**
     * 开始播放按钮
     */
    void onStartPlay();

    /**
     * 返回
     */
    void onBack();

    /**
     * 全屏
     */
    void onFullScreen();

    /**
     * 错误后的重试
     */
    void onRetry(int errorStatus);
}
