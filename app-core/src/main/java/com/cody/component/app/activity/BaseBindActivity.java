/*
 * ************************************************************
 * 文件：BaseBindActivity.java  模块：component.app-core  项目：component
 * 当前修改时间：2021年03月03日 23:46:06
 * 上次修改时间：2021年02月27日 16:19:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.app-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.app.activity;

import android.os.Bundle;
import android.view.View;

import com.cody.component.bind.CoreBR;
import com.cody.component.bind.IBinding;
import com.cody.component.handler.data.IViewData;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by xu.yi. on 2019/3/25.
 * dataBinding 基类
 */
public abstract class BaseBindActivity<B extends ViewDataBinding> extends BaseActivity implements IBinding<B>, View.OnClickListener {
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBaseReady(savedInstanceState);
    }

    /**
     * 子Activity逻辑重载这个函数
     */
    @CallSuper
    protected void onBaseReady(Bundle savedInstanceState) {
        mBinding = DataBindingUtil.setContentView(this, getLayoutID());
        if (mBinding != null) {
            mBinding.setLifecycleOwner(this);
            mBinding.setVariable(CoreBR.onClickListener, this);
            bindViewData();
        } else {
            setContentView(getLayoutID());
        }

        if (isSupportImmersive()) {
            onImmersiveReady();
        }
    }

    /**
     * @param variableId the CoreBR id of the variable to be set. For example, if the variable is
     *                   <code>x</code>, then variableId will be <code>CoreBR.x</code>.
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
    public boolean unBound() {
        return mBinding == null;
    }

    @Override
    public B getBinding() {
        if (mBinding == null) {
            throw new NullPointerException("You should bindViewData first!");
        }
        return mBinding;
    }
}
