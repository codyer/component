/*
 * ************************************************************
 * 文件：AbsListActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年10月26日 12:26:39
 * 上次修改时间：2019年10月26日 11:57:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.IBaseListView;
import com.cody.component.bind.adapter.list.BindingListAdapter;
import com.cody.component.bind.adapter.list.MultiBindingListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.viewmodel.ListViewModel;
import com.cody.component.util.RecyclerViewUtil;

/**
 * 无分页列表 使用List 做列表页面，刷新，初始化，出错提示，重试, 可以包含头尾
 */
public abstract class AbsListActivity<B extends ViewDataBinding, VM extends ListViewModel<?, ?>> extends FriendlyBindActivity<B, VM> implements IBaseListView, OnBindingItemClickListener {
    private BindingListAdapter<ItemViewDataHolder> mListAdapter;

    @NonNull
    @Override
    public BindingListAdapter<ItemViewDataHolder> getListAdapter() {
        if (mListAdapter == null) {
            mListAdapter = buildListAdapter();
        }
        return mListAdapter;
    }

    @NonNull
    @Override
    public LinearLayoutManager buildLayoutManager() {
        return new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
    }

    @Override
    public boolean childHandleScrollVertically(final View target, final int direction) {
        return RecyclerViewUtil.canScrollVertically(getRecyclerView(),direction);
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        getListAdapter().setItemClickListener(this);
        getListAdapter().setItemLongClickListener(this);
        getRecyclerView().setLayoutManager(buildLayoutManager());
        getRecyclerView().setAdapter(getListAdapter());

        getViewModel().getItems().observe(this, items -> getViewModel().getRequestStatusLive().observe(this, new Observer<RequestStatus>() {
            @Override
            public void onChanged(final RequestStatus requestStatus) {
                if (requestStatus.isLoaded()) {
                    getViewModel().getRequestStatusLive().removeObserver(this);
                    getListAdapter().submitList(items);
                }
            }
        }));
    }

    @Override
    protected void onRequestStatus(final RequestStatus requestStatus) {
        super.onRequestStatus(requestStatus);
        if (getListAdapter() instanceof MultiBindingListAdapter) {
            ((MultiBindingListAdapter) getListAdapter()).setRequestStatus(requestStatus);
        }
    }

    @Override
    public void scrollToTop() {
        if (unBound())return;
        RecyclerViewUtil.smoothScrollToTop(getRecyclerView());
    }
}
