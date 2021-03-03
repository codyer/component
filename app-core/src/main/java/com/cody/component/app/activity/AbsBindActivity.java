/*
 * ************************************************************
 * 文件：AbsBindActivity.java  模块：component.app-core  项目：component
 * 当前修改时间：2021年03月03日 23:46:06
 * 上次修改时间：2021年02月28日 17:59:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.app-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.app.activity;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cody.component.bind.CoreBR;
import com.cody.component.handler.data.ViewData;
import com.cody.component.handler.viewmodel.BaseViewModel;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewData
 */
public abstract class AbsBindActivity<B extends ViewDataBinding, VM extends BaseViewModel, VD extends ViewData> extends BaseActionbarActivity<B> {
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
        if (mViewModel == null){// 检查是否需要自己构建viewModel
            mViewModel = buildViewModel();
        }else {// 已经构建过不需要重复构建
            return mViewModel;
        }
        if (mViewModel == null) {// 使用默认构造函数创建
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