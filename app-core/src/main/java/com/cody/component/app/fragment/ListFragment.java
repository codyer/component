/*
 * ************************************************************
 * 文件：ListFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年07月22日 14:14:00
 * 上次修改时间：2019年07月03日 17:54:13
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.IBaseListView;
import com.cody.component.app.R;
import com.cody.component.app.databinding.FragmentListBinding;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.viewmodel.ListViewModel;

/**
 * 无分页列表 使用List 做列表页面，刷新，初始化，出错提示，重试，下拉刷新
 */
public abstract class ListFragment<VM extends ListViewModel<?, ?>> extends AbsListFragment<FragmentListBinding, VM> implements IBaseListView, OnBindingItemClickListener {

    @Override
    public FriendlyLayout getFriendlyLayout() {
        return getBinding().friendlyView;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_list;
    }

    @NonNull
    @Override
    public RecyclerView getRecyclerView() {
        return getBinding().recyclerView;
    }
}