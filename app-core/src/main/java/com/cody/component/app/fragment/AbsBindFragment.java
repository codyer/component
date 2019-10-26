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


import androidx.databinding.ViewDataBinding;

import com.cody.component.bind.CoreBR;
import com.cody.component.handler.data.ViewData;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewModel
 */
public abstract class AbsBindFragment<B extends ViewDataBinding, VD extends ViewData> extends BaseBindFragment<B> {
    protected abstract VD getViewData();

    @Override
    protected void bindViewData() {
        bindViewData(CoreBR.viewData, getViewData());
    }
}