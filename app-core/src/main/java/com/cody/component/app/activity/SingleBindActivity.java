/*
 * ************************************************************
 * 文件：SingleBindActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import com.cody.component.app.BR;
import com.cody.component.lib.data.IViewData;

import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewModel
 */
public abstract class SingleBindActivity<B extends ViewDataBinding, VD extends IViewData> extends BaseBindActivity<B> {
    protected abstract VD getViewData();

    @Override
    protected void bindViewData() {
        bindViewData(BR.viewData, getViewData());
    }
}
