/*
 * ************************************************************
 * 文件：BaseLazyFragment.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月24日 18:15:07
 * 上次修改时间：2018年08月29日 16:42:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 懒加载
 */
public abstract class BaseLazyFragment extends BaseFragment {

    private boolean mIsFirstVisible = true;
    private boolean mIsPrepared = false;

    protected abstract void onBaseReady(Bundle savedInstanceState);

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected void onFirstUserVisible(@Nullable Bundle savedInstanceState) {
        onBaseReady(savedInstanceState);
    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected void onUserVisible() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mIsPrepared = true;
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare(savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mIsPrepared) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onFirstUserVisible(null);
            } else {
                onUserVisible();
            }
        }
    }

    private void initPrepare(Bundle savedInstanceState) {
        if (getUserVisibleHint()) {
            if (mIsFirstVisible) {
                mIsFirstVisible = false;
                onFirstUserVisible(savedInstanceState);
            } else {
                onUserVisible();
            }
        }
    }
}
