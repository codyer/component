/*
 * ************************************************************
 * 文件：BindListFragment.java  模块：app-core  项目：component
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

import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.app.IBaseListView;
import com.cody.component.app.R;
import com.cody.component.app.databinding.FragmentBindListBinding;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.viewmodel.MultiListViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BindListFragment extends SingleBindFragment<FragmentBindListBinding, MaskViewData> implements IBaseListView, OnBindingItemClickListener {
    protected MultiBindingPageListAdapter mListAdapter;

    @NonNull
    @Override
    public MultiListViewModel<?> getListViewModel() {
        return getViewModel(getVMClass());
    }

    @Override
    protected MaskViewData getViewData() {
        return getListViewModel().getMaskViewData();
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        mListAdapter = getListAdapter();
        mListAdapter.setItemClickListener(this);
        mListAdapter.setItemLongClickListener(this);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        getBinding().recyclerView.setAdapter(mListAdapter);
        getBinding().swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, android.R.color.holo_orange_dark);
        getBinding().swipeRefreshLayout.setOnRefreshListener(() -> getListViewModel().refresh());
        getListViewModel().getRequestStatus().observe(this, requestStatus -> {
            getBinding().swipeRefreshLayout.setRefreshing(requestStatus.isLoading());
            if (mListAdapter.getItemCount() == 0) {//本来为空
                if (requestStatus.isError()) {
                    getViewData().failedView(requestStatus.getMessage());
                } else if (requestStatus.isEmpty()) {
                    getViewData().noContentView();
                } else {
                    getViewData().hideMaskView();
                }
            } else {// 本来有数据
                getViewData().hideMaskView();
            }
            mListAdapter.setRequestStatus(requestStatus);
        });
        getListViewModel().getOperation().observe(this, operation -> mListAdapter.setOperation(operation));
        getListViewModel().getPagedList().observe(this, items -> mListAdapter.submitList(items));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bind_list;
    }

    @Override
    public void retry() {
        getListViewModel().retry();
    }
}
