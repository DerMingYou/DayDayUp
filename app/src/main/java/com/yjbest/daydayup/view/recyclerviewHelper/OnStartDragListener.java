package com.yjbest.daydayup.view.recyclerviewHelper;

import android.support.v7.widget.RecyclerView;

/**
 * Description:
 * Data：2019/2/14-16:15
 * Author: DerMing_You
 */
public interface OnStartDragListener {
    /**
     * 当View需要拖拽时回调
     *
     * @param viewHolder The holder of view to drag
     */
    void onStartDrag(RecyclerView.ViewHolder viewHolder);
}
