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
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.IBaseListView;
import com.cody.component.app.R;
import com.cody.component.app.databinding.FragmentPageListBinding;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.viewmodel.PageListViewModel;

/**
 * 使用pageList 做列表页面，自动分页，刷新，初始化，加载更多，出错提示，重试，下拉刷新
 */
public abstract class PageListBindFragment<VM extends PageListViewModel<FriendlyViewData, ?>> extends FriendlyBindFragment<FragmentPageListBinding, VM, FriendlyViewData> implements IBaseListView, OnBindingItemClickListener {
    protected MultiBindingPageListAdapter mListAdapter;

    @Override
    public FriendlyLayout getFriendlyLayout() {
        return getBinding().friendlyView;
    }

    @Override
    public boolean childHandleScrollVertically(final View target, final int direction) {
        int topRowVerticalPosition = getBinding().recyclerView.getChildCount() == 0 ? 0 : getBinding().recyclerView.getChildAt(0).getTop();
        return topRowVerticalPosition < 0 || getBinding().recyclerView.canScrollVertically(direction);
    }

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

        getFriendlyViewModel().getPagedList().observe(this, items -> getFriendlyViewModel().getRequestStatusLive().observe(this, new Observer<RequestStatus>() {
            @Override
            public void onChanged(final RequestStatus requestStatus) {
                if (requestStatus.isLoaded()) {
                    getFriendlyViewModel().getRequestStatusLive().removeObserver(this);
                    mListAdapter.submitList(items);
                }
            }
        }));
    }

    @Override
    protected void onRequestStatus(final RequestStatus requestStatus) {
        super.onRequestStatus(requestStatus);
        mListAdapter.setRequestStatus(requestStatus);
    }

    @Override
    public void scrollToTop() {
    }
}
