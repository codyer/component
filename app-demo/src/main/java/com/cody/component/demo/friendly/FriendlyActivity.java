/*
 * ************************************************************
 * 文件：FriendlyActivity.java  模块：app-demo  项目：component
 * 当前修改时间：2019年06月13日 17:34:56
 * 上次修改时间：2019年06月13日 17:34:56
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-demo
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo.friendly;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.cody.component.app.activity.AbsBindActivity;
import com.cody.component.app.widget.friendly.IFriendlyView;
import com.cody.component.demo.R;
import com.cody.component.demo.databinding.ActivityFriendlyBinding;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.Refreshable;
import com.cody.component.handler.viewmodel.BaseViewModel;


public class FriendlyActivity extends AbsBindActivity<ActivityFriendlyBinding, BaseViewModel, FriendlyViewData> implements Refreshable {
    private RequestStatus mRequestStatus = new RequestStatus();

    @Override
    protected FriendlyViewData getViewData() {
        return new FriendlyViewData();
    }

    @NonNull
    @Override
    public Class<BaseViewModel> getVMClass() {
        return BaseViewModel.class;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.activity_friendly;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        getBinding().friendlyView.setIFriendlyView(new IFriendlyView() {
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
            public void refresh() {
                FriendlyActivity.this.refresh();
            }

            @Override
            public LifecycleOwner getLifecycleOwner() {
                return FriendlyActivity.this;
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.initView:
                refresh();
                break;
            case R.id.emptyView:
                refresh();
                break;
            case R.id.errorView:
                refresh();
                break;
        }
    }

    int i = 0;

    @Override
    public void refresh() {
        getBinding().friendlyView.submitStatus(mRequestStatus = mRequestStatus.refresh());
        getBinding().friendlyView.postDelayed(() -> {
            if (i % 3 == 0) {
                getBinding().friendlyView.submitStatus(mRequestStatus = mRequestStatus.error("失败"));
            } else if (i % 3 == 1) {
                getBinding().friendlyView.submitStatus(mRequestStatus = mRequestStatus.empty());
            } else {
                getBinding().friendlyView.submitStatus(mRequestStatus = mRequestStatus.loaded());
            }
            i++;
        }, 1000);
    }
}
