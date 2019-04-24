/*
 * ************************************************************
 * 文件：BindFriendlyFragment.java  模块：app-core  项目：component
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

import com.cody.component.app.R;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.interfaces.Refreshable;
import com.cody.component.handler.viewmodel.BaseViewModel;
import com.cody.component.handler.viewmodel.FriendlyViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

/**
 * 包含出错刷新重试布局，需要在自己的布局 include mask_view
 * <p>
 * <include
 * layout="@layout/mask_view"
 * bind:onClickListener="@{onClickListener}"
 * bind:viewData="@{viewData}" />
 */
public abstract class BindFriendlyFragment<B extends ViewDataBinding, VD extends MaskViewData> extends SingleBindFragment<B, VD> implements Refreshable, OnRetryListener {

    public abstract <VM extends FriendlyViewModel<VD>> VM getFriendlyViewModel();

    @SuppressWarnings("unchecked")
    @Override
    public <VM extends BaseViewModel> VM getViewModel(@NonNull final Class<VM> viewModelClass) {
        return (VM) getFriendlyViewModel();
    }

    @Override
    protected VD getViewData() {
        return getFriendlyViewModel().getFriendlyViewData();
    }

    @Override
    public void retry() {
        getFriendlyViewModel().retry();
    }

    @Override
    public void refresh() {
        getFriendlyViewModel().refresh();
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        getFriendlyViewModel().getRequestStatus().observe(this, requestStatus -> {
            if (getViewData().getVisibility().get()) {//本来没有数据
                if (requestStatus.isError()) {
                    getViewData().failedView(requestStatus.getMessage());
                } else if (requestStatus.isEmpty()) {
                    getViewData().noContentView();
                } else if (requestStatus.isLoading()) {
                    getViewData().startLoading();
                } else {
                    getViewData().hideMaskView();
                }
            } else {// 本来有数据
                getViewData().hideMaskView();
            }
        });
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.friendlyRetry) {
            if (!getViewData().getLoading().get()) {
                getFriendlyViewModel().retry();
            }
        }
    }
}
