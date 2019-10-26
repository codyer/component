/*
 * ************************************************************
 * 文件：PageListFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.IBasePageListView;
import com.cody.component.app.R;
import com.cody.component.app.databinding.FragmentListBinding;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.viewmodel.PageListViewModel;

/**
 * 使用pageList 做列表页面，自动分页，刷新，初始化，加载更多，出错提示，重试，下拉刷新, 可以包含头尾
 */
public abstract class PageListFragment<VM extends PageListViewModel<FriendlyViewData, ?>> extends AbsPageListFragment<FragmentListBinding, VM> implements IBasePageListView, OnBindingItemClickListener {

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