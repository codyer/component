/*
 * ************************************************************
 * 文件：IBaseView.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.view;

import com.cody.component.handler.BaseViewModel;
import com.cody.component.lib.view.IView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by xu.yi. on 2019/3/25.
 * p-gbb-android
 */
public interface IBaseView extends IView {
    default <T extends BaseViewModel> T getViewModel(@NonNull Class<T> viewModelClass) {
        return getViewModel(viewModelClass, null);
    }

    <T extends BaseViewModel> T getViewModel(@NonNull Class<T> viewModelClass, @Nullable ViewModelProvider.Factory factory);
}
