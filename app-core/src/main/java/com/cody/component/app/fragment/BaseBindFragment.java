/*
 * ************************************************************
 * 文件：BaseBindFragment.java  模块：bind-ui  项目：component
 * 当前修改时间：2019年04月05日 17:32:51
 * 上次修改时间：2019年04月04日 13:35:31
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.component.lib.data.IViewData;
import com.cody.component.lib.bind.IBinding;
import com.cody.component.app.BR;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * dataBinding 基类
 */
public abstract class BaseBindFragment<B extends ViewDataBinding> extends BaseFragment implements IBinding, View.OnClickListener {
    private B mBinding;

    /**
     * 使用如下函数添加binding
     *
     * @see #bindViewData(int, IViewData)
     */
    protected abstract void bindViewData();

    /**
     * 子类提供有binding的资源ID
     */
    @LayoutRes
    protected abstract int getLayoutID();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (getLayoutID() == 0) {
            return view;
        }
        if (!isBound()) {
            mBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false);
        }
        if (mBinding != null) {
            mBinding.setLifecycleOwner(this);
            mBinding.setVariable(BR.onClickListener, this);
            bindViewData();
            view = mBinding.getRoot();
        } else {
            view = inflater.inflate(getLayoutID(), container, false);
        }
        return view;
    }

    @CallSuper
    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        // The same thing as activity has been done in onCreateView
    }
    /**
     * @param variableId the BR id of the variable to be set. For example, if the variable is
     *                   <code>x</code>, then variableId will be <code>BR.x</code>.
     * @param viewData   The new viewData of the variable to be set.
     */
    public void bindViewData(@IdRes int variableId, @Nullable IViewData viewData) {
        if (mBinding != null) {
            mBinding.setVariable(variableId, viewData);
        }
    }

    /**
     * 是否已经设置bind
     */
    @Override
    public boolean isBound() {
        return mBinding != null;
    }

    @Override
    public B getBinding() {
        if (mBinding == null) {
            throw new NullPointerException("You should bindViewData first!");
        }
        return mBinding;
    }
}
