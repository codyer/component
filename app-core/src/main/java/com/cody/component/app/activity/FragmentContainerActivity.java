/*
 * ************************************************************
 * 文件：FragmentContainerActivity.java  模块：app-core  项目：component
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
import androidx.fragment.app.Fragment;

import com.cody.component.app.R;
import com.cody.component.app.databinding.ActivityFragmentContainerBinding;
import com.cody.component.handler.interfaces.Scrollable;

/**
 * 包含返回键和头部
 */
public abstract class FragmentContainerActivity extends BaseFragmentContainerActivity<ActivityFragmentContainerBinding> implements Scrollable {
    public abstract Fragment getFragment();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment_container;
    }

    protected Toolbar getToolbar() {
        return getBinding().toolbar;
    }

}