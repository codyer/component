/*
 * ************************************************************
 * 文件：FragmentContainerWithButtonActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月14日 17:02:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;


import androidx.appcompat.widget.Toolbar;

import com.cody.component.app.R;
import com.cody.component.app.databinding.ActivityFragmentContainerWithButtonBinding;
import com.cody.component.handler.interfaces.Scrollable;

/**
 * 包含返回键和头部和底部按钮
 */
public abstract class FragmentContainerWithButtonActivity extends BaseFragmentContainerActivity<ActivityFragmentContainerWithButtonBinding> implements Scrollable {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment_container_with_button;
    }

    @Override
    protected Toolbar getToolbar() {
        return getBinding().toolbar;
    }
}