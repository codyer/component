/*
 * ************************************************************
 * 文件：FriendlyBindFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月24日 10:18:27
 * 上次修改时间：2019年04月24日 09:48:02
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cody.component.app.R;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.app.widget.friendly.IFriendlyView;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.handler.viewmodel.FriendlyViewModel;

/**
 * 包含出错刷新重试布局，根布局使用 FriendlyLayout
 * <p>
 * <p>
 * bind:onClickListener="@{onClickListener}"
 * bind:viewData="@{viewData}"
 */
public abstract class FriendlyBindFragment<B extends ViewDataBinding, VM extends FriendlyViewModel<VD>, VD extends FriendlyViewData> extends SimpleBindFragment<B, VD> implements IFriendlyView, OnRetryListener {

    public abstract FriendlyLayout getFriendlyLayout();

    /**
     * 创建 viewModel 实例，注意初始化 viewData
     */
    public abstract VM buildFriendlyViewModel();

    @NonNull
    public abstract Class<VM> getVMClass();

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
    public boolean canScrollVertically(final View target, final int direction) {
        return target.canScrollVertically(direction);
    }

    @Nullable
    @Override
    public LifecycleOwner getLifecycleOwner() {
        return this;
    }

    @Override
    protected void onFirstUserVisible(Bundle savedInstanceState) {
        getFriendlyViewModel().onInit();
        super.onFirstUserVisible(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    @Deprecated
    @Override
    public <D extends BaseViewModel> D getViewModel(@NonNull final Class<D> viewModelClass) {
        return (D) getFriendlyViewModel();
    }

    public VM getFriendlyViewModel() {
        return getViewModel(getVMClass(), new ViewModelProvider.Factory() {
            @NonNull
            @Override
            @SuppressWarnings("unchecked")
            public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
                return (T) buildFriendlyViewModel();
            }
        });
    }

    @Override
    protected VD getViewData() {
        return getFriendlyViewModel().getFriendlyViewData();
    }

    @Override
    public void retry() {
        if (isAdded()) {
            getFriendlyViewModel().retry();
        }
    }

    @Override
    public void refresh() {
        if (isAdded()) {
            getFriendlyViewModel().refresh();
        }
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (getFriendlyLayout() != null) {
            getFriendlyLayout().setIFriendlyView(this);
        }

        getFriendlyViewModel().getRequestStatusLive().observe(this, this::onRequestStatus);
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
