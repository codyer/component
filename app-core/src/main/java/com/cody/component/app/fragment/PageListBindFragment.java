/*
 * ************************************************************
 * 文件：PageListBindFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import android.os.Bundle;

import com.cody.component.app.IBaseListView;
import com.cody.component.app.R;
import com.cody.component.app.databinding.FragmentPageListBinding;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.viewmodel.PageListViewModel;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 使用pageList 做列表页面，自动分页，刷新，初始化，加载更多，出错提示，重试，下拉刷新
 */
public abstract class PageListBindFragment<VM extends PageListViewModel<MaskViewData>> extends FriendlyBindFragment<FragmentPageListBinding, VM, MaskViewData> implements IBaseListView, OnBindingItemClickListener {
    protected MultiBindingPageListAdapter mListAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_page_list;
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        mListAdapter = buildListAdapter();
        mListAdapter.setItemClickListener(this);
        mListAdapter.setItemLongClickListener(this);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        getBinding().recyclerView.setAdapter(mListAdapter);
        getBinding().swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        getBinding().swipeRefreshLayout.setOnRefreshListener(() -> getFriendlyViewModel().refresh());
        getFriendlyViewModel().getPagedList().observe(this, items -> getFriendlyViewModel().getRequestStatusLive().observe(this, new Observer<RequestStatus>() {
            @Override
            public void onChanged(final RequestStatus requestStatus) {
                if (!requestStatus.isLoading()) {
                    getFriendlyViewModel().getRequestStatusLive().removeObserver(this);
                    mListAdapter.submitList(items);
                }
            }
        }));
    }

    @Override
    protected void onRequestStatus(final RequestStatus requestStatus) {
        super.onRequestStatus(requestStatus);
        if (getBinding().swipeRefreshLayout.isRefreshing()) {
            getBinding().swipeRefreshLayout.setRefreshing(requestStatus.isLoading());
        }
        mListAdapter.setRequestStatus(requestStatus);
    }
}
