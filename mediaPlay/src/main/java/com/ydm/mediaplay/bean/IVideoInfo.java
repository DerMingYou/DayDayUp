package com.ydm.mediaplay.bean;

import java.io.Serializable;

/**
 * Description:
 * Data：2019/1/30-14:48
 * Author: DerMing_You
 */
public interface IVideoInfo extends Serializable {
    /**
     * 视频标题
     */
    String getVideoTitle();

    /**
     * 视频播放路径（本地或网络）
     */
    String getVideoPath();
}
