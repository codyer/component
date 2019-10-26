/*
 * ************************************************************
 * 文件：FriendlyBindActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月13日 17:04:10
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.cody.component.app.R;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.app.widget.friendly.IFriendlyView;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.viewmodel.FriendlyViewModel;

/**
 * 包含出错刷新重试布局，根布局使用 FriendlyLayout
 * <p>
 * <p>
 * bind:onClickListener="@{onClickListener}"
 * bind:viewData="@{viewData}"
 */
public abstract class FriendlyBindActivity<B extends ViewDataBinding, VM extends FriendlyViewModel<?>> extends AbsBindActivity<B, VM, FriendlyViewData> implements IFriendlyView, OnRetryListener {

    public abstract FriendlyLayout getFriendlyLayout();

    @Override
    public int initView() {
        return R.layout.friendly_init_view;
    }

    @Override
    public int emptyView() {
        return R.layout.friendly_empty_view;
    }

    @Override
    public int errorView() {
        return R.layout.friendly_error_view;
    }

    @Override
    public boolean childHandleScrollVertically(final View target, final int direction) {
        return target.canScrollVertically(direction);
    }

    @Nullable
    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    public void retry() {
        getViewModel().retry();
    }

    @Override
    public void refresh() {
        getViewModel().refresh();
    }

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getViewModel().onInit();
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (getFriendlyLayout() != null) {
            getFriendlyLayout().setIFriendlyView(this);
        }
        getViewModel().getRequestStatusLive().observe(this, this::onRequestStatus);
    }

    /**
     * 请求状态变更
     */
    protected void onRequestStatus(final RequestStatus requestStatus) {
        if (getFriendlyLayout() != null) {
            getFriendlyLayout().submitStatus(requestStatus);
        }
    }

    @CallSuper
    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.errorView) {
            retry();
        } else if (v.getId() == R.id.emptyView) {
            refresh();
        }
    }
}
