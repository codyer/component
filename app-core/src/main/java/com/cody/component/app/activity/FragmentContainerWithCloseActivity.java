/*
 * ************************************************************
 * 文件：FragmentContainerWithCloseActivity.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月30日 12:57:45
 * 上次修改时间：2019年04月26日 22:46:37
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

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.cody.component.app.R;
import com.cody.component.app.databinding.ActivityFragmentContainerBinding;
import com.cody.component.app.databinding.ActivityFragmentContainerWithCloseBinding;
import com.cody.component.handler.interfaces.Scrollable;
import com.cody.component.handler.livedata.BooleanLiveData;

/**
 * 包含返回键和头部和快速关闭按钮
 */
public abstract class FragmentContainerWithCloseActivity extends EmptyBindActivity<ActivityFragmentContainerWithCloseBinding> implements Scrollable {
    public abstract Fragment getFragment();

    @Override
    protected int getLayoutID() {
        return R.layout.activity_fragment_container_with_close;
    }

    protected boolean isShowBack() {
        return true;
    }

    protected boolean isShowTitle() {
        return true;
    }

    private BooleanLiveData mQuickClose = new BooleanLiveData(false);

    public BooleanLiveData getQuickClose() {
        return mQuickClose;
    }

    @Override
    public void setTitle(final int title) {
        super.setTitle(title);
        getBinding().title.setText(title);
    }

    @Override
    public void setTitle(final CharSequence title) {
        super.setTitle(title);
        getBinding().title.setText(title);
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (!isBound()) return;
        setSupportActionBar(getBinding().toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getBinding().back.setVisibility(isShowBack() ? View.VISIBLE : View.INVISIBLE);
            getBinding().title.setVisibility(isShowTitle() ? View.VISIBLE : View.INVISIBLE);
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
        mQuickClose.observe(this, show -> getBinding().quickClose.setVisibility(show ? View.VISIBLE : View.GONE));
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
        } else if (v.getId() == R.id.back) {
            onBackPressed();
        }else if (v.getId() == R.id.quickClose) {
            finish();
        }
    }
}
