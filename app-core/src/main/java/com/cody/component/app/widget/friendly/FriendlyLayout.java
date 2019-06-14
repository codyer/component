/*
 * ************************************************************
 * 文件：FriendlyLayout.java  模块：app-core  项目：component
 * 当前修改时间：2019年06月11日 14:10:14
 * 上次修改时间：2019年06月11日 14:10:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.widget.friendly;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cody.component.app.R;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.define.Status;
import com.cody.component.util.LogUtil;

/**
 * Created by xu.yi. on 2019-06-11.
 * component 包含
 * 初始化页面 RUNNING init
 * 出错页面 FAILED
 * 空页面 EMPTY
 * 正常页面xml中嵌入
 *
 * @see com.cody.component.handler.define.Status
 * RUNNING,
 * SUCCESS,
 * FAILED,
 * EMPTY,
 * CANCEL,
 * END
 * <p>
 * bind:onClickListener="@{onClickListener}"
 * bind:viewData="@{viewData}"
 */
public class FriendlyLayout extends SwipeRefreshLayout {
    FrameLayout frameLayout;
    private Context mContext;
    private IFriendlyView mIFriendlyView;
    private FriendlyViewData mFriendlyViewData;
    private OnClickListener mOnClickListener;
    private Status mStatus;

    private View mInitView;
    private View mFailedView;
    private View mEmptyView;

    public void setIFriendlyView(final IFriendlyView IFriendlyView) {
        mIFriendlyView = IFriendlyView;
        if (frameLayout != null) {
            frameLayout.addView(new LinearLayout(mContext), new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            ));
            frameLayout.addView(new LinearLayout(mContext), new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            ));
            frameLayout.addView(new LinearLayout(mContext), new FrameLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT
            ));
        } else {
            LogUtil.e("setIFriendlyView +++++++");
        }
    }

    /**
     * bind:viewData="@{viewData}"
     */
    public void setViewData(final FriendlyViewData viewData) {
        mFriendlyViewData = viewData;
    }

    /**
     * bind:onClickListener="@{onClickListener}"
     */
    @Override
    public void setOnClickListener(final OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setStatus(final Status status) {
        mStatus = status;
    }

    public FriendlyLayout(@NonNull final Context context) {
        super(context);
        mContext = context;
        initView(context);
    }

    public FriendlyLayout(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        frameLayout = new FrameLayout(mContext);
        super.addView(frameLayout, getChildCount(), new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));
    }

    @Override
    public void addView(final View child) {
        if (frameLayout != null) {
            frameLayout.addView(child);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(final View child, final int index) {
        if (frameLayout != null) {
            frameLayout.addView(child, index);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(final View child, final int width, final int height) {
        if (frameLayout != null) {
            frameLayout.addView(child, width, height);
        } else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(final View child, final LayoutParams params) {
        if (frameLayout != null) {
            frameLayout.addView(child, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(final View child, final int index, final LayoutParams params) {
        if (frameLayout != null) {
            frameLayout.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }
}
