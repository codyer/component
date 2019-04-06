/*
 * ************************************************************
 * 文件：SingleBindFragment.java  模块：bind-ui  项目：component
 * 当前修改时间：2019年04月05日 17:32:51
 * 上次修改时间：2019年04月04日 13:35:31
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.base.app.fragment;


import com.cody.component.view.data.IViewData;
import com.cody.component.base.BR;

import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewModel
 */
public abstract class SingleBindFragment<B extends ViewDataBinding, VD extends IViewData> extends BaseBindFragment<B> {
    protected abstract VD getViewData();

    @Override
    protected void bindViewData() {
        bindViewData(BR.viewData, getViewData());
    }
}