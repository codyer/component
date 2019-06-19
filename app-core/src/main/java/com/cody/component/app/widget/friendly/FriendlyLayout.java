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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.cody.component.app.R;
import com.cody.component.bind.CoreBR;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.Operation;
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
    private FrameLayout mFrameLayout;
    private Context mContext;
    private IFriendlyView mIFriendlyView;
    private FriendlyViewData mFriendlyViewData;
    private OnClickListener mOnClickListener;

    private View mInitView;
    private View mErrorView;
    private View mEmptyView;
    private boolean mInitialized = false;

    public void setIFriendlyView(final IFriendlyView IFriendlyView) {
        mIFriendlyView = IFriendlyView;
        initViewBind();
    }

    /**
     * bind:viewData="@{viewData}"
     */
    public void setViewData(final FriendlyViewData viewData) {
        mFriendlyViewData = viewData;
        initViewBind();
    }

    /**
     * bind:onClickListener="@{onClickListener}"
     */
    @Override
    public void setOnClickListener(final OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void removeFriendLyView() {
        setEnabled(true);
        if (mFrameLayout != null) {
            mFrameLayout.removeView(mInitView);
            mFrameLayout.removeView(mEmptyView);
            mFrameLayout.removeView(mErrorView);
        }
    }

    public void submitStatus(final RequestStatus status) {
        if (status != null) {
            if (status.getStatus() != Status.RUNNING) {
                setRefreshing(false);
            }
            if (status.getOperation() == Operation.INIT) {
                mInitialized = false;
            }
            if (mFriendlyViewData != null) {
                mFriendlyViewData.getMessage().setValue(status.getMessage());
            }
            switch (status.getStatus()) {
                case RUNNING:
                    if (!mInitialized) {
                        removeFriendLyView();
                        addToLayout(mInitView);
                    }
                    break;
                case SUCCESS:
                    removeFriendLyView();
                    mInitialized = true;
                    break;
                case EMPTY:
                    removeFriendLyView();
                    addToLayout(mEmptyView);
                    mInitialized = false;
                    break;
                case END:
                    removeFriendLyView();
                    mInitialized = true;
                    break;
                case CANCEL:
                    removeFriendLyView();
                    break;
                case FAILED:
                    if (!mInitialized) {
                        removeFriendLyView();
                        addToLayout(mErrorView);
                    }
                    break;
            }
        }
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
        mFrameLayout = new FrameLayout(mContext);
        super.addView(mFrameLayout, getChildCount(), new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));

        setColorSchemeResources(R.color.uiColorPrimary, R.color.uiColorPrimaryAccent, R.color.uiColorYellow);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mIFriendlyView != null) {
            if (mFrameLayout != null) {
                View target = mFrameLayout.getChildAt(0);
                if (target != null) {
                    return mIFriendlyView.canScrollVertically(target, -1);
                }
            }
        }
        return super.canChildScrollUp();
    }

    @Override
    public void addView(final View child) {
        if (mFrameLayout != null) {
            mFrameLayout.addView(child);
        } else {
            super.addView(child);
        }
    }

    @Override
    public void addView(final View child, final int index) {
        if (mFrameLayout != null) {
            mFrameLayout.addView(child, index);
        } else {
            super.addView(child, index);
        }
    }

    @Override
    public void addView(final View child, final int width, final int height) {
        if (mFrameLayout != null) {
            mFrameLayout.addView(child, width, height);
        } else {
            super.addView(child, width, height);
        }
    }

    @Override
    public void addView(final View child, final LayoutParams params) {
        if (mFrameLayout != null) {
            mFrameLayout.addView(child, params);
        } else {
            super.addView(child, params);
        }
    }

    @Override
    public void addView(final View child, final int index, final LayoutParams params) {
        if (mFrameLayout != null) {
            mFrameLayout.addView(child, index, params);
        } else {
            super.addView(child, index, params);
        }
    }

    private void initViewBind() {
        if (mIFriendlyView == null || mInitView != null || mErrorView != null || mEmptyView != null) return;
        if (mFrameLayout != null && mFriendlyViewData != null) {
            if (mIFriendlyView.initView() > 0) {
                ViewDataBinding initViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), mIFriendlyView.initView(), null, false);
                mInitView = initViewBinding.getRoot();
                initViewBinding.setLifecycleOwner(mIFriendlyView.getLifecycleOwner());
                initViewBinding.setVariable(CoreBR.viewData, mFriendlyViewData);
                initViewBinding.setVariable(CoreBR.onClickListener, mOnClickListener);
                initViewBinding.executePendingBindings();
            }
            if (mIFriendlyView.errorView() > 0) {
                ViewDataBinding errorViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), mIFriendlyView.errorView(), null, false);
                mErrorView = errorViewBinding.getRoot();
                errorViewBinding.setLifecycleOwner(mIFriendlyView.getLifecycleOwner());
                errorViewBinding.setVariable(CoreBR.viewData, mFriendlyViewData);
                errorViewBinding.setVariable(CoreBR.onClickListener, mOnClickListener);
                errorViewBinding.executePendingBindings();
            }
            if (mIFriendlyView.emptyView() > 0) {
                ViewDataBinding emptyViewBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), mIFriendlyView.emptyView(), null, false);
                mEmptyView = emptyViewBinding.getRoot();
                emptyViewBinding.setVariable(CoreBR.viewData, mFriendlyViewData);
                emptyViewBinding.setLifecycleOwner(mIFriendlyView.getLifecycleOwner());
                emptyViewBinding.setVariable(CoreBR.onClickListener, mOnClickListener);
                emptyViewBinding.executePendingBindings();
            }

            setOnRefreshListener(() -> mIFriendlyView.refresh());
        } else {
            LogUtil.e("setIFriendlyView +++++++");
        }
    }

    private void addToLayout(final View view) {
        setEnabled(false);
        mFrameLayout.addView(view, new FrameLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        ));
    }
}
