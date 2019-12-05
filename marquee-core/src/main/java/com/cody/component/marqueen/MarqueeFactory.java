/*
 * ************************************************************
 * 文件：MarqueeFactory.java  模块：component  项目：component
 * 当前修改时间：2019年12月05日 14:40:04
 * 上次修改时间：2019年03月01日 19:38:59
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.marqueen;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cody.yi on 2017/8/1.
 * some useful information
 */
public abstract class MarqueeFactory<E> {
    protected Context mContext;
    private MarqueeView mMarqueeView;
    private Handler mHandler;
    private List<E> mDataList;
    public MarqueeFactory(Context context, MarqueeView marqueeView) {
        mContext = context;
        mMarqueeView = marqueeView;
    }

    public abstract View createItemView(int type, E data);

    public abstract int getViewType(int position);

    //适用于多次（含一次）更新数据源
    public void setData(final List<E> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        mDataList = list;
        List<View> views = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            E data = list.get(i);
            View view = createItemView(getViewType(i), data);
            if (view == null) continue;
            ViewGroup group = (ViewGroup) view.getParent();
            if (group != null) {
                group.removeAllViews();
            }
            views.add(view);
        }

        if (mMarqueeView != null) {
            mMarqueeView.setMarqueeViews(views);
        }
    }

    //适用于多次（含一次）更新数据源 避免重影
    public void resetData(final List<E> list){
        if (null == list || list.size() == 0) return;
        if (mDataList == null || mDataList.size() == 0){
            setData(list);
            return;
        }
        //正在执行动画时 等执行完后再刷新数据
        if (mMarqueeView.getOutAnimation() != null) {
            mMarqueeView.getOutAnimation().setAnimationListener(new Animation.AnimationListener() {
                boolean isAnimationStopped = false;

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (!isAnimationStopped) {
                        if (mHandler == null){
                            mHandler = new Handler();
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                setData(list);
                                isAnimationStopped = true;
                            }
                        });
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }

    }

    public MarqueeView getMarqueeView() {
        return mMarqueeView;
    }
}
