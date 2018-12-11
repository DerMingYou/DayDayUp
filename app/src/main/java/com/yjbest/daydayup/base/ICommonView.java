package com.yjbest.daydayup.base;

/**
 * Description:
 * Data：2018/10/27-14:50
 * Author: DerMing_You
 */
public interface ICommonView {

    /**
     * 提示出错信息
     *
     * @param msg msg
     */
    void showToastMsg(String msg);

    /**
     * 显示加载对话框
     */
    void showLoadingDialog();

    /**
     * 隐藏加载对话框
     */
    void hideLoadingDialog();

    /**
     * 显示没有网络布局
     */
    void showNoNetworkLayout(String msg);
}
