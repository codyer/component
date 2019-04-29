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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 *
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
        getFriendlyViewModel().getPagedList().observe(this, items -> mListAdapter.submitList(items));
    }

    @Override
    protected void onRequestStatus(final RequestStatus requestStatus) {
        super.onRequestStatus(requestStatus);
        getBinding().swipeRefreshLayout.setRefreshing(requestStatus.isLoading());
        mListAdapter.setRequestStatus(requestStatus);
    }
}
