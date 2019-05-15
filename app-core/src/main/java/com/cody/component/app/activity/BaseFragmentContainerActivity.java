/*
 * ************************************************************
 * 文件：BaseFragmentContainerActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月15日 09:54:14
 * 上次修改时间：2019年05月15日 09:54:14
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

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cody.component.app.R;
import com.cody.component.handler.interfaces.Scrollable;

/**
 * fragment 容器基类
 */
public abstract class BaseFragmentContainerActivity<B extends ViewDataBinding> extends EmptyBindActivity<B> implements Scrollable {
    public abstract Fragment getFragment();

    protected boolean isShowBack() {
        return true;
    }

    protected boolean isShowTitle() {
        return true;
    }

    protected abstract Toolbar getToolbar();

    /**
     * 修改显示或者隐藏头部
     */
    public void showHeader(boolean show) {
        if (getSupportActionBar() != null) {
            getToolbar().setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 修改显示或者隐藏头部
     */
    public void showTitle(boolean show) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(show);
            getSupportActionBar().setDisplayShowTitleEnabled(show);
        }
    }

    @Override
    public void setTitle(final int title) {
        super.setTitle(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (unBound()) return;
        setSupportActionBar(getToolbar());
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
