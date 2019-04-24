/*
 * ************************************************************
 * 文件：BaseViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:51:36
 * 上次修改时间：2019年04月23日 18:23:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;


import com.cody.component.handler.action.ViewAction;
import com.cody.component.lib.safe.SafeMutableLiveData;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

/**
 * Created by xu.yi. on 2019/3/25.
 * 封装基本逻辑
 */
public class BaseViewModel extends ViewModel implements IViewModel {
    private final SafeMutableLiveData<ViewAction> mViewActionLiveData;
    protected LifecycleOwner mLifecycleOwner;

    @Override
    protected void onCleared() {
        super.onCleared();
        //cancel http request
    }

    public BaseViewModel() {
        mViewActionLiveData = new SafeMutableLiveData<>();
    }

    @Override
    final public void showLoading() {
        setAction(ViewAction.SHOW_LOADING);
    }

    @Override
    final public void showLoading(String message) {
        setAction(ViewAction.SHOW_LOADING, message);
    }

    @Override
    final public void hideLoading() {
        setAction(ViewAction.HIDE_LOADING);
    }

    @Override
    final public void showToast(String message) {
        setAction(ViewAction.SHOW_TOAST, message);
    }

    @Override
    final public void finish() {
        setAction(ViewAction.FINISH);
    }

    @Override
    final public void finishWithResultOk() {
        setAction(ViewAction.FINISH_WITH_RESULT_OK);
    }

    @Override
    final public void executeAction(ViewAction action) {
        mViewActionLiveData.setValue(action);
    }

    @Override
    final public SafeMutableLiveData<ViewAction> getActionLiveData() {
        return mViewActionLiveData;
    }

    @Override
    public  <T extends BaseViewModel> T setLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.mLifecycleOwner = lifecycleOwner;
        //noinspection unchecked
        return (T) this;
    }

    final protected void setAction(int action) {
        setAction(action, null);
    }

    final protected void setAction(int action, String message) {
        executeAction(new ViewAction(action, message));
    }
}
