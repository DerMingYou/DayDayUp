package com.yjbest.daydayup.http.bean;

import com.ydm.mediaplay.bean.IVideoInfo;

/**
 * Description:
 * Data：2019/1/30-16:22
 * Author: DerMing_You
 */
public class VideoBean implements IVideoInfo {
    private String title;

    private String path;

    @Override
    public String getVideoTitle() {
        return title;
    }

    @Override
    public String getVideoPath() {
        return path;
    }

    public VideoBean(String title, String path) {
        this.title = title;
        this.path = path;
    }

}
