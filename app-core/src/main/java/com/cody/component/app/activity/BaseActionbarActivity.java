/*
 * ************************************************************
 * 文件：BaseActionbarActivity.java  模块：component.app-core  项目：component
 * 当前修改时间：2021年03月03日 23:46:06
 * 上次修改时间：2021年02月28日 18:09:34
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.app-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.app.activity;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.cody.component.handler.interfaces.Scrollable;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.ViewDataBinding;

/**
 * 包含自定义actionbar 基类
 */
public abstract class BaseActionbarActivity<B extends ViewDataBinding> extends BaseBindActivity<B> implements Scrollable {
    @Override
    protected void bindViewData() {
    }

    protected boolean isShowBack() {
        return true;
    }

    protected boolean isShowTitle() {
        return true;
    }

    protected int getToolbarId() {
        return -1;
    }

    protected Toolbar getToolbar() {
        return null;
    }

    /**
     * 修改显示或者隐藏头部
     */
    public void showHeader(boolean show) {
        if (getSupportActionBar() != null && getToolbar() != null) {
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
        if (getToolbar() != null) {
            setSupportActionBar(getToolbar());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(isShowBack());
                getSupportActionBar().setDisplayShowTitleEnabled(isShowTitle());
            }
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
        if (getToolbarId() != -1 && v.getId() == getToolbarId()) {
            scrollToTop();
        }
    }

    @Override
    public void scrollToTop() {

    }
}
