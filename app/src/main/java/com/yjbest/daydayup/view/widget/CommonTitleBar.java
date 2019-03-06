package com.yjbest.daydayup.view.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yjbest.daydayup.R;
import com.yjbest.daydayup.util.ScreenParameterUtils;

/**
 * Description:
 * Data：2019/3/6-10:54
 * Author: DerMing_You
 */
public class CommonTitleBar extends RelativeLayout {
    private ImageView ivRight;
    private TextView tvTitle, tvRight;
    private ImageButton ibBack;
    private LinearLayout llCommonTitle;
    private View vLineTop;
    private Context context;

    public CommonTitleBar(Context context) {
        super(context, null);
        this.context = context;
    }

    public CommonTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View.inflate(context, R.layout.common_bar, this);
        ibBack = (ImageButton) findViewById(R.id.ib_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvRight = (TextView) findViewById(R.id.tv_right);
        ivRight = (ImageView) findViewById(R.id.image_right);
        llCommonTitle = (LinearLayout) findViewById(R.id.common_title);
        vLineTop = (View) findViewById(R.id.v_line_top);

        //setHeaderHeight();
    }

    public void setHeaderHeight() {
        llCommonTitle.setPadding(0, ScreenParameterUtils.getStatusBarHeight(context), 0, 0);
        llCommonTitle.requestLayout();
    }

    /**
     * 管理返回按钮
     */
    public void setBackVisibility(boolean visible) {
        if (visible) {
            ibBack.setVisibility(View.VISIBLE);
        } else {
            ibBack.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧字符串
     *
     * @param visible
     */
    public void setTvLeftVisiable(boolean visible) {
        if (visible) {
            ibBack.setVisibility(View.VISIBLE);
        } else {
            ibBack.setVisibility(View.GONE);
        }
    }


    /**
     * 管理标题
     */
    public void setTitleVisibility(boolean visible) {
        if (visible) {
            tvTitle.setVisibility(View.VISIBLE);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
    }

    public void setTitleText(String string) {
        tvTitle.setText(string);
    }

    public void setTitleText(int string) {
        tvTitle.setText(string);
    }

    public void setTitleColor(int color) {
        tvTitle.setTextColor(color);
    }

    /**
     * 右图标
     */
    public void setRightImagVisibility(boolean visible) {
        ivRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightImagSrc(int id) {
        ivRight.setVisibility(View.VISIBLE);
        ivRight.setImageResource(id);
    }

    /**
     * 获取右按钮
     *
     * @return
     */
    public View getRightImage() {
        return ivRight;
    }

    /**
     * 获取右文本
     *
     * @return
     */
    public TextView getRightText() {
        return tvRight;
    }

    /**
     * 获取下划线
     *
     * @return
     */
    public View getLineTop() {
        return vLineTop;
    }

    /**
     * 左图标
     *
     * @param id
     */
    public void setLeftImagSrc(int id) {
        ibBack.setBackgroundResource(id);
    }

    /**
     * 右标题
     */
    public void setRightTitleVisibility(boolean visible) {
        tvRight.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setRightTitle(String text) {
        tvRight.setText(text);
        tvRight.setVisibility(VISIBLE);
    }

    public String getRightTitle() {
        return tvRight.getText().toString();
    }


    /**
     * 下划线是否隐藏
     */
    public void setLineTopVisibility(boolean visible) {
        vLineTop.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    /*
     * 点击事件
     */
    public void setOnBackListener(OnClickListener listener) {
        ibBack.setOnClickListener(listener);
    }

    public void setOnRightImagListener(OnClickListener listener) {
        ivRight.setOnClickListener(listener);
    }

    public void setOnRightTextListener(OnClickListener listener) {
        tvRight.setOnClickListener(listener);
    }

    /**
     * 标题背景颜色
     *
     * @param color
     */
    public void setBackGroundColor(int color) {
        llCommonTitle.setBackgroundColor(color);
    }

    public Drawable getBackGroundDrawable() {
        return llCommonTitle.getBackground();
    }
}
