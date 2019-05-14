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


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cody.component.app.R;
import com.cody.component.app.databinding.ActivityFragmentContainerBinding;
import com.cody.component.handler.interfaces.Scrollable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * 包含返回键和头部
 */
public abstract class FragmentContainerActivity extends EmptyBindActivity<ActivityFragmentContainerBinding> implements Scrollable {
    public abstract Fragment getFragment();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment_container;
    }

    protected boolean isShowBack() {
        return true;
    }

    protected boolean isShowTitle() {
        return true;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (unBound()) return;
        setSupportActionBar(getBinding().toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack());
            getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle());
        }
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

    //添加点击返回箭头事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isShowBack()) {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.toolbar) {
            scrollToTop();
        }
    }
}
