/*
 * ************************************************************
 * 文件：EmptyBindFragment.java  模块：bind-ui  项目：component
 * 当前修改时间：2019年04月05日 17:32:51
 * 上次修改时间：2019年04月01日 17:10:50
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * 不需要绑定viewData
 */
public abstract class EmptyBindFragment<B extends ViewDataBinding> extends BaseBindFragment<B> {
    @Override
    protected void bindViewData() {
    }
}
