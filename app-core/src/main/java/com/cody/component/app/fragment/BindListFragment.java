/*
 * ************************************************************
 * 文件：BindListFragment.java  模块：bind-app  项目：component
 * 当前修改时间：2019年04月09日 18:08:46
 * 上次修改时间：2019年04月09日 16:56:47
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import android.os.Bundle;

import com.cody.component.adapter.list.OnBindingItemClickListener;
import com.cody.component.app.IBaseListView;
import com.cody.component.app.R;
import com.cody.component.list.data.StubViewData;
import com.cody.component.app.databinding.FragmentBindListBinding;
import com.cody.component.list.adapter.MultiBindingPageListAdapter;
import com.cody.component.list.data.ItemMultiViewData;
import com.cody.component.list.viewmodel.MultiListViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BindListFragment<IVD extends ItemMultiViewData> extends SingleBindFragment<FragmentBindListBinding, StubViewData> implements IBaseListView<IVD>, OnBindingItemClickListener {
    private MultiBindingPageListAdapter<IVD> mListAdapter;

/*    @Override
    public MultiBindingPageListAdapter<IVD> getListAdapter() {
        return new MultiBindingPageListAdapter<IVD>(this, this) {
        };
    }*/

    @NonNull
    @Override
    final public <VM extends MultiListViewModel<IVD, ?>> VM getListViewModel() {
        return getViewModel(getVMClass());
    }

    @Override
    protected StubViewData getViewData() {
        return getListViewModel().getStubViewData();
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
            mListAdapter.setRequestStatus(requestStatus);
            if (requestStatus.isError()) {
                getViewData().failedView(requestStatus.getMessage());
            } else if (requestStatus.isLoaded()) {
                if (mListAdapter.getItemCount() == 0) {
                    getViewData().noContentView();
                } else {
                    getViewData().noStubView();
                }
            }
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
