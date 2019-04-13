/*
 * ************************************************************
 * 文件：IBaseListView.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app;

import com.cody.component.list.adapter.MultiBindingPageListAdapter;
import com.cody.component.list.data.ItemMultiViewData;
import com.cody.component.list.listener.OnRetryListener;
import com.cody.component.list.viewmodel.MultiListViewModel;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/4/10.
 * 列表绑定需要实现的接口
 */
public interface IBaseListView<IVD extends ItemMultiViewData> extends OnRetryListener {
    @NonNull
    <VM extends MultiListViewModel<IVD, ?>> Class<VM> getVMClass();

    @NonNull
    <VM extends MultiListViewModel<IVD, ?>> VM getListViewModel();

    @NonNull
    MultiBindingPageListAdapter<IVD> getListAdapter();
}
