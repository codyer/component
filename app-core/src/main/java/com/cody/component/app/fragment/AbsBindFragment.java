/*
 * ************************************************************
 * 文件：AbsBindFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月24日 20:05:59
 * 上次修改时间：2019年04月24日 18:51:59
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;


import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cody.component.bind.CoreBR;
import com.cody.component.handler.data.ViewData;
import com.cody.component.handler.viewmodel.BaseViewModel;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewModel
 */
public abstract class AbsBindFragment<B extends ViewDataBinding, VM extends BaseViewModel, VD extends ViewData> extends BaseBindFragment<B> {
    protected abstract VD getViewData();
    private VM mViewModel;

    /**
     * 创建 viewModel 实例，注意初始化 viewData
     * 需要自己初始化ViewModel才需要重载，否则不需要重载
     */
    public VM buildViewModel() {
        return null;
    }

    @NonNull
    public abstract Class<VM> getVMClass();

    @Override
    protected void bindViewData() {
        bindViewData(CoreBR.viewData, getViewData());
    }

    public VM getViewModel() {
        if (mViewModel == null){
            mViewModel = buildViewModel();
        }
        if (mViewModel == null) {
           return mViewModel = getViewModel(getVMClass());
        }else {
            return mViewModel = getViewModel(getVMClass(), new ViewModelProvider.Factory() {
                @NonNull
                @Override
                @SuppressWarnings("unchecked")
                public <T extends ViewModel> T create(@NonNull final Class<T> modelClass) {
                    return (T) mViewModel;
                }
            });
        }
    }
}