package com.yjbest.daydayup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.yjbest.daydayup.R;
import com.yjbest.daydayup.base.BaseActivity;
import com.yjbest.daydayup.http.bean.ItemEntity;
import com.yjbest.daydayup.view.adapter.DragItemAdapter;
import com.yjbest.daydayup.view.recyclerviewHelper.MyItemTouchHelperCallback;
import com.yjbest.daydayup.view.recyclerviewHelper.OnStartDragListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description: 列表拖动
 * Data：2019/2/14-15:57
 * Author: DerMing_You
 */
public class DragItemActivity extends BaseActivity implements OnStartDragListener {

    @BindView(R.id.rvDragItem)
    RecyclerView rvDragItem;

    private List<ItemEntity> mList;
    private ItemTouchHelper mItemTouchHelper;

    public static void launch(Context context) {
        Intent intent = new Intent(context, DragItemActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drag_item;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        rvDragItem.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        DragItemAdapter adapter = new DragItemAdapter(mList,this);
        adapter.setOnClickSwitchListener(new DragItemAdapter.OnClickSwitchListener() {
            @Override
            public void onClick(int position, boolean isChecked) {
                //实际开发中做发送请求等等一些处理
            }
        });
        rvDragItem.setAdapter(adapter);
        mItemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(adapter));
        mItemTouchHelper.attachToRecyclerView(rvDragItem);
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        String [] strings = {"Android","后端","前端","iOS","人工智能","产品","工具资源","阅读","设计"};
        for (String string : strings) {
            ItemEntity item = new ItemEntity();
            item.setChecked(false);
            item.setName(string);
            mList.add(item);
        }
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        //通知ItemTouchHelper开始拖拽
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void showNoNetworkLayout(String msg) {

    }
}
