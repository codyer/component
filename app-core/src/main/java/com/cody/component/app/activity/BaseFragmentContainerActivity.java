/*
 * ************************************************************
 * 文件：BaseFragmentContainerActivity.java  模块：component.app-core  项目：component
 * 当前修改时间：2021年03月03日 23:46:06
 * 上次修改时间：2021年02月28日 10:26:47
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.app-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.app.activity;


import android.os.Bundle;

import com.cody.component.app.R;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * fragment 容器基类
 */
public abstract class BaseFragmentContainerActivity<B extends ViewDataBinding> extends BaseActionbarActivity<B> {
    public abstract Fragment getFragment();

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = getFragment();
            if (fragment == null) {
                finish();
                return;
            }
            manager.beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
