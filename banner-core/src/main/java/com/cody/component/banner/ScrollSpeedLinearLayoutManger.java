/*
 * ************************************************************
 * 文件：ScrollSpeedLinearLayoutManger.java  模块：banner-core  项目：component
 * 当前修改时间：2019年10月25日 09:31:54
 * 上次修改时间：2019年10月25日 09:31:53
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：banner-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.banner;

/**
 * Created by xu.yi. on 2019-10-25.
 * component
 */

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 控制滑动速度的LinearLayoutManager
 */
public class ScrollSpeedLinearLayoutManger extends LinearLayoutManager {
    private float MILLISECONDS_PER_INCH = 0.5f;
    private Context context;

    public ScrollSpeedLinearLayoutManger(Context context) {
        super(context);
        this.context = context;
    }

    public ScrollSpeedLinearLayoutManger(final Context context, final int orientation, final boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public ScrollSpeedLinearLayoutManger(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    public PointF computeScrollVectorForPosition(int targetPosition) {
                        return ScrollSpeedLinearLayoutManger.this
                                .computeScrollVectorForPosition(targetPosition);
                    }

                    //This returns the milliseconds it takes to
                    //scroll one pixel.
                    @Override
                    protected float calculateSpeedPerPixel
                    (DisplayMetrics displayMetrics) {
                        return MILLISECONDS_PER_INCH / displayMetrics.density;
                        //返回滑动一个pixel需要多少毫秒
                    }

                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    public void setSpeed(float speed) {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * speed;
    }

    public void setSpeedSlow() {
        //自己在这里用density去乘，希望不同分辨率设备上滑动速度相同
        //0.3f是自己估摸的一个值，可以根据不同需求自己修改
        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.5f;
    }

    public void setSpeedFast() {
        MILLISECONDS_PER_INCH = context.getResources().getDisplayMetrics().density * 0.05f;
    }
}
