/*
 * ************************************************************
 * 文件：AbsPageListActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年10月26日 12:27:09
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

import com.cody.component.app.IBasePageListView;
import com.cody.component.bind.adapter.list.BindingPageListAdapter;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.viewmodel.AbsPageListViewModel;
import com.cody.component.util.RecyclerViewUtil;

/**
 * 使用pageList 做列表页面，自动分页，刷新，初始化，加载更多，出错提示，重试，下拉刷新, 可以包含头尾
 */
public abstract class AbsPageListActivity<B extends ViewDataBinding, VM extends AbsPageListViewModel<?, ?>> extends FriendlyBindActivity<B, VM> implements IBasePageListView, OnBindingItemClickListener {
    private BindingPageListAdapter<ItemViewDataHolder> mListAdapter;

    @NonNull
    @Override
    public BindingPageListAdapter<ItemViewDataHolder> getListAdapter() {
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
        return RecyclerViewUtil.canScrollVertically(getRecyclerView(), direction);
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        getListAdapter().setItemClickListener(this);
        getListAdapter().setItemLongClickListener(this);
        getRecyclerView().setLayoutManager(buildLayoutManager());
        getRecyclerView().setAdapter(getListAdapter());

        getViewModel().getPagedList().observe(this, items -> getViewModel().getRequestStatusLive().observe(this, new Observer<RequestStatus>() {
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
        if (getListAdapter() instanceof MultiBindingPageListAdapter) {
            ((MultiBindingPageListAdapter) getListAdapter()).setRequestStatus(requestStatus);
        }
    }

    @Override
    public void scrollToTop() {
        RecyclerViewUtil.smoothScrollToTop(getRecyclerView());
    }
}
