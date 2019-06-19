/*
 * ************************************************************
 * 文件：BindingBanner.java  模块：bind-banner  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月13日 08:44:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-banner
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.banner;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cody.component.banner.adapter.BindingBannerAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cody.yi on 2017/4/26.
 * bindingBanner
 */
public class BindingBanner extends FrameLayout {

    private static final int DEFAULT_SELECTED_COLOR = 0xffffffff;
    private static final int DEFAULT_UNSELECTED_COLOR = 0x50ffffff;
    private static final int INDICATOR_MARGIN_BOTTOM = dp2px(15);

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                Resources.getSystem().getDisplayMetrics());
    }

    private int mInterval;
    private final int mSize;
    private final int mSpace;
    private int mStartX;
    private int mStartY;
    private int mCurrentIndex = Integer.MAX_VALUE / 2;

    private boolean mIsShowIndicator;
    private boolean mIsPlaying;
    private boolean mIsTouched;
    private boolean mIsAutoPlaying;

    private final Drawable mSelectedDrawable;
    private final Drawable mUnselectedDrawable;

    private final RecyclerView mRecyclerView;
    private final LinearLayout mLinearLayout;

    private BindingBannerAdapter mBindingBannerAdapter;

    private final Handler mHandler = new Handler();

    private final Runnable mPlayTask = new Runnable() {

        @Override
        public void run() {
            if (Integer.MAX_VALUE - mCurrentIndex < mBindingBannerAdapter.getBannerSize()) {
                int halfSize = Integer.MAX_VALUE / (mBindingBannerAdapter.getBannerSize() * 2);
                mCurrentIndex = halfSize * mBindingBannerAdapter.getBannerSize();
            }
            mRecyclerView.scrollToPosition(mCurrentIndex++);
            mRecyclerView.smoothScrollToPosition(mCurrentIndex);
            if (mIsShowIndicator) {
                switchIndicator();
            }
            mHandler.removeCallbacksAndMessages(null);
            mHandler.postDelayed(this, mInterval);
        }
    };

    public BindingBanner(Context context) {
        this(context, null);
    }

    public BindingBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BindingBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BindingBanner);
        mInterval = a.getInt(R.styleable.BindingBanner_b_Interval, 3000);
        mIsShowIndicator = a.getBoolean(R.styleable.BindingBanner_b_ShowIndicator, true);
        mIsAutoPlaying = a.getBoolean(R.styleable.BindingBanner_b_AutoPlaying, true);
        Drawable sd = a.getDrawable(R.styleable.BindingBanner_b_IndicatorSelectedSrc);
        Drawable usd = a.getDrawable(R.styleable.BindingBanner_b_IndicatorUnselectedSrc);
        if (sd == null) {
            mSelectedDrawable = generateDefaultDrawable(DEFAULT_SELECTED_COLOR);
        } else {
            if (sd instanceof ColorDrawable) {
                mSelectedDrawable = generateDefaultDrawable(((ColorDrawable) sd).getColor());
            } else {
                mSelectedDrawable = sd;
            }
        }
        if (usd == null) {
            mUnselectedDrawable = generateDefaultDrawable(DEFAULT_UNSELECTED_COLOR);
        } else {
            if (usd instanceof ColorDrawable) {
                mUnselectedDrawable = generateDefaultDrawable(((ColorDrawable) usd).getColor());
            } else {
                mUnselectedDrawable = usd;
            }
        }
        mSize = a.getDimensionPixelSize(R.styleable.BindingBanner_b_IndicatorSize, 0);
        mSpace = a.getDimensionPixelSize(R.styleable.BindingBanner_b_IndicatorSpace, dp2px(4));
        int margin = a.getDimensionPixelSize(R.styleable.BindingBanner_b_IndicatorMargin, dp2px(8));
        int g = a.getInt(R.styleable.BindingBanner_b_IndicatorGravity, 1);
        int gravity;
        if (g == 0) {
            gravity = GravityCompat.START;
        } else if (g == 2) {
            gravity = GravityCompat.END;
        } else {
            gravity = Gravity.CENTER;
        }
        a.recycle();

        mRecyclerView = new RecyclerView(context) {
            @Override
            public boolean canScrollHorizontally(final int direction) {
                return true;//解决滑动冲突 https://www.jianshu.com/p/43befa4224c9
            }
        };
        mLinearLayout = new LinearLayout(context);

        new PagerSnapHelper().attachToRecyclerView(mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        int first = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                        int last = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                        if (first == last && mCurrentIndex != last) {
                            mCurrentIndex = last;
                            if (mIsShowIndicator && mIsTouched) {
                                mIsTouched = false;
                                switchIndicator();
                            }
                        }
                    }
                }
            }
        });
        mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        mLinearLayout.setGravity(Gravity.CENTER);

        LayoutParams bannerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutParams indicatorParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        indicatorParams.gravity = Gravity.BOTTOM | gravity;
        indicatorParams.setMargins(margin, 0, margin, INDICATOR_MARGIN_BOTTOM);
        addView(mRecyclerView, bannerParams);
        addView(mLinearLayout, indicatorParams);
    }

    public void setBindingBannerAdapter(BindingBannerAdapter bindingBannerAdapter) {
        setPlaying(false);
        mBindingBannerAdapter = bindingBannerAdapter;
        mRecyclerView.setAdapter(mBindingBannerAdapter);

        if (mBindingBannerAdapter.getBannerSize() > 1) {
            int halfSize = Integer.MAX_VALUE / (mBindingBannerAdapter.getBannerSize() * 2);
            mCurrentIndex = halfSize * mBindingBannerAdapter.getBannerSize();
            mRecyclerView.scrollToPosition(mCurrentIndex);
            if (mIsShowIndicator) {
                createIndicators();
            }
            setPlaying(true);
        } else {
            mRecyclerView.scrollToPosition(mCurrentIndex);
            mLinearLayout.removeAllViews();
        }
    }

    public BindingBannerAdapter getBindingBannerAdapter() {
        return mBindingBannerAdapter;
    }

    /**
     * 设置是否显示指示器导航点
     *
     * @param show 显示
     */
    public void setShowIndicator(boolean show) {
        this.mIsShowIndicator = show;
    }

    /**
     * 设置轮播间隔时间
     *
     * @param millisecond 时间毫秒
     */
    public void setInterval(int millisecond) {
        this.mInterval = millisecond;
    }

    /**
     * 设置是否禁止滚动播放
     *
     * @param isAutoPlaying true  是自动滚动播放,false 是禁止自动滚动
     */
    public void setAutoPlaying(boolean isAutoPlaying) {
        this.mIsAutoPlaying = isAutoPlaying;
    }

    /**
     * 获取RecyclerView实例，便于满足自定义{@link RecyclerView.ItemAnimator}或者{@link RecyclerView.Adapter}的需求
     *
     * @return RecyclerView实例
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //手动触摸的时候，停止自动播放，根据手势变换
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) ev.getX();
                mStartY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int disX = moveX - mStartX;
                int disY = moveY - mStartY;
                boolean hasMoved = 2 * Math.abs(disX) > Math.abs(disY);
                getParent().requestDisallowInterceptTouchEvent(hasMoved);
                if (hasMoved) {
                    setPlaying(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (!mIsPlaying) {
                    mIsTouched = true;
                    setPlaying(true);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setPlaying(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setPlaying(false);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == GONE || visibility == INVISIBLE) {
            // 停止轮播
            setPlaying(false);
        } else if (visibility == VISIBLE) {
            // 开始轮播
            setPlaying(true);
        }
        super.onWindowVisibilityChanged(visibility);
    }

    /**
     * 指示器整体由数据列表容量数量的AppCompatImageView均匀分布在一个横向的LinearLayout中构成
     * 使用AppCompatImageView的好处是在Fragment中也使用Compat相关属性
     */
    private void createIndicators() {
        mLinearLayout.removeAllViews();
        for (int i = 0; i < mBindingBannerAdapter.getBannerSize(); i++) {
            AppCompatImageView img = new AppCompatImageView(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = mSpace / 2;
            lp.rightMargin = mSpace / 2;
            if (mSize >= dp2px(4)) { // 设置了indicatorSize属性
                lp.width = lp.height = mSize;
            } else {
                // 如果设置的resource.xml没有明确的宽高，默认最小2dp，否则太小看不清
                img.setMinimumWidth(dp2px(2));
                img.setMinimumHeight(dp2px(2));
            }
            img.setImageDrawable(i == 0 ? mSelectedDrawable : mUnselectedDrawable);
            mLinearLayout.addView(img, lp);
        }
    }

    /**
     * 默认指示器是一系列直径为6dp的小圆点
     */
    private GradientDrawable generateDefaultDrawable(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setSize(dp2px(6), dp2px(6));
        gradientDrawable.setCornerRadius(dp2px(6));
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    private synchronized void setPlaying(boolean playing) {
        if (mIsAutoPlaying) {
            if (!mIsPlaying && playing && mBindingBannerAdapter != null && mBindingBannerAdapter.getBannerSize() > 2) {
                mHandler.removeCallbacksAndMessages(null);
                mHandler.postDelayed(mPlayTask, mInterval);
                mIsPlaying = true;
            } else if (mIsPlaying && !playing) {
                mHandler.removeCallbacksAndMessages(null);
                mIsPlaying = false;
            }
        }
    }

    /**
     * 改变导航的指示点
     */
    private void switchIndicator() {
        if (mLinearLayout != null && mLinearLayout.getChildCount() > 0) {
            for (int i = 0; i < mLinearLayout.getChildCount(); i++) {
                ((AppCompatImageView) mLinearLayout.getChildAt(i)).setImageDrawable(
                        i == mBindingBannerAdapter.getPosition(mCurrentIndex) ? mSelectedDrawable : mUnselectedDrawable);
            }
        }
    }

    private class PagerSnapHelper extends LinearSnapHelper {

        @Override
        public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
            int targetPos = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
            final View currentView = findSnapView(layoutManager);
            if (targetPos != RecyclerView.NO_POSITION && currentView != null) {
                int currentPos = layoutManager.getPosition(currentView);
                int first = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                int last = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                currentPos = targetPos < currentPos ? last : (targetPos > currentPos ? first : currentPos);
                targetPos = targetPos < currentPos ? currentPos - 1 : (targetPos > currentPos ? currentPos + 1 :
                        currentPos);
            }
            return targetPos;
        }
    }
}