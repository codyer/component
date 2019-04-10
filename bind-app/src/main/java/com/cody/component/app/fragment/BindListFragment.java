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
import android.view.View;

import com.cody.component.app.R;
import com.cody.component.app.data.StubViewData;
import com.cody.component.app.databinding.FragmentBindListBinding;
import com.cody.component.list.adapter.MultiBindingPageListAdapter;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.viewmodel.MultiListViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class BindListFragment extends SingleBindFragment<FragmentBindListBinding, StubViewData> {
    private MultiBindingPageListAdapter mListAdapter;

    @Override
    protected StubViewData getViewData() {
        return new StubViewData();
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        getBinding().recyclerView.setAdapter(mListAdapter = new MultiBindingPageListAdapter(this) {

            @Override
            public void retry() {
                getViewModel(MultiListViewModel.class).retry();
            }
        });
        getBinding().swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_dark, android.R.color.holo_blue_dark,
                android.R.color.holo_orange_dark);
        getBinding().swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getViewModel(MultiListViewModel.class).refresh();
            }
        });
        MutableLiveData<RequestStatus> requestStatus = getViewModel(MultiListViewModel.class).getRequestStatus();
        requestStatus.observe(this, requestStatus1 -> mListAdapter.setRequestStatus(requestStatus1));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_bind_list;
    }

    @Override
    public void onClick(View v) {

    }
}
