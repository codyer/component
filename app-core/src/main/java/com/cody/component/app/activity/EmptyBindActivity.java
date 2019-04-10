/*
 * ************************************************************
 * 文件：EmptyBindActivity.java  模块：bind-base  项目：component
 * 当前修改时间：2019年04月05日 19:14:06
 * 上次修改时间：2019年04月05日 17:44:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;

import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * 不需要绑定viewData
 */
public abstract class EmptyBindActivity<B extends ViewDataBinding> extends BaseBindActivity<B> {
    @Override
    protected void bindViewData() {
    }
}
