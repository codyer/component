/*
 * ************************************************************
 * 文件：IViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月14日 00:14:46
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler;

import com.cody.component.handler.action.ViewAction;
import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.lib.view.IView;

import androidx.lifecycle.LifecycleOwner;

/**
 * Created by xu.yi. on 2019/3/25.
 * component
 */
public interface IViewModel extends IView {
    SafeMutableLiveData<ViewAction> getActionLiveData();

    /**
     * 处理其他action，扩展用
     */
    void executeAction(ViewAction action);
    <T extends BaseViewModel> T setLifecycleOwner(LifecycleOwner lifecycleOwner);
}
