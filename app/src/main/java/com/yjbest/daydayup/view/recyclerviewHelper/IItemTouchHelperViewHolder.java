package com.yjbest.daydayup.view.recyclerviewHelper;

/**
 * Description:
 * Data：2019/2/14-16:14
 * Author: DerMing_You
 */
public interface IItemTouchHelperViewHolder {
    /**
     * item被选中，在侧滑或拖拽过程中更新状态
     */
    void onItemSelected();

    /**
     * item的拖拽或侧滑结束，恢复默认的状态
     */
    void onItemClear();
}
