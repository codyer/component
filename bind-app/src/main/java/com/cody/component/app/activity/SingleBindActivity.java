/*
 * ************************************************************
 * 文件：SingleBindActivity.java  模块：bind-ui  项目：component
 * 当前修改时间：2019年04月05日 17:32:51
 * 上次修改时间：2019年03月29日 17:42:56
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import com.cody.component.app.BR;
import com.cody.component.view.data.IViewData;

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
