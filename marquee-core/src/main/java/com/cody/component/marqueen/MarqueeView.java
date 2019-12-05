/*
 * ************************************************************
 * 文件：MarqueeView.java  模块：component  项目：component
 * 当前修改时间：2019年12月05日 14:40:11
 * 上次修改时间：2018年08月30日 20:14:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.marqueen;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;


import com.cody.component.marquee.R;

import java.util.List;

/**
 * Created by Cody.yi on 16/5/31.
 * 滚动view
 */
public class MarqueeView extends ViewFlipper {
    private Context mContext;
    /**
     * 当前消息位置
     */
    private int mPosition;
    /**
     * 动画是否开始,防止
     */
    private boolean isAnimStarted = false;
    private OnItemClickListener mOnItemClickListener;

    public MarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initAnimation();
    }

    private void initAnimation() {
        clearAnimation();
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.core_anim_marquee_in);
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.core_anim_marquee_out);
        setOutAnimation(animOut);
    }

    public void setMarqueeViews(final List<View> views) {
        post(new Runnable() {
            @Override
            public void run() {
                //避免重影
                removeAllViews();
                mPosition = 0;

                if (views == null) return;
                addItemView(views.get(mPosition));
                initAnimation();
                startFlipping();
                mPosition++;
                if (getInAnimation() != null) {
                    getInAnimation().setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            if (isAnimStarted) {
                                animation.cancel();
                            }
                            isAnimStarted = true;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            if (mPosition < views.size()) {
                                addItemView(views.get(mPosition++));
                            }
                            isAnimStarted = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                }
            }
        });
    }

    private void addItemView(final View view) {
        final int position = mPosition;
        //设置监听回调
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(position, view);
                }
            }
        });
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null) {
            group.removeAllViews();
        }
        addView(view);
    }

    /**
     * 设置监听接口
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * item_view的接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}
