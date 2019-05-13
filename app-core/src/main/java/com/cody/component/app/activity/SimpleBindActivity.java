/*
 * ************************************************************
 * 文件：SimpleBindActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月13日 17:02:49
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import androidx.databinding.ViewDataBinding;

import com.cody.component.bind.CoreBR;
import com.cody.component.handler.data.ViewData;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewModel
 */
public abstract class SimpleBindActivity<B extends ViewDataBinding, VD extends ViewData> extends BaseBindActivity<B> {
    protected abstract VD getViewData();

    @Override
    protected void bindViewData() {
        bindViewData(CoreBR.viewData, getViewData());
    }
}
