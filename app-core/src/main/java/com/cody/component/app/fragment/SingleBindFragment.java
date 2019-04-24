/*
 * ************************************************************
 * 文件：SingleBindFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 14:08:23
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;


import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.viewmodel.SingleViewModel;

import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * 一个页面只绑定一个viewModel
 */
public abstract class SingleBindFragment<B extends ViewDataBinding, VM extends SingleViewModel<VD>, VD extends MaskViewData> extends FriendlyBindFragment<B, VM, VD> {
    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        getFriendlyViewModel().OnInit();
    }
}