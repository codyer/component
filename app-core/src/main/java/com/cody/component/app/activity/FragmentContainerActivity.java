/*
 * ************************************************************
 * 文件：FragmentContainerActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月11日 15:55:42
 * 上次修改时间：2019年04月11日 15:55:42
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.activity;


import android.os.Bundle;

import com.cody.component.app.R;
import com.cody.component.app.databinding.ActivityFragmentContainerBinding;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public abstract class FragmentContainerActivity extends EmptyBindActivity<ActivityFragmentContainerBinding> {
    public abstract Fragment getFragment();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment_container;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = getFragment();
        if (fragment == null) {
            finish();
            return;
        }
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
