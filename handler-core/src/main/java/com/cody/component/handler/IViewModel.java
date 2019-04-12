/*
 * ************************************************************
 * 文件：IViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:20
 * 上次修改时间：2019年04月11日 15:06:51
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

/**
 * Created by xu.yi. on 2019/3/25.
 * p-gbb-android
 */
public interface IViewModel extends IView {
    SafeMutableLiveData<ViewAction> getActionLiveData();

    /**
     * 处理其他action，扩展用
     */
    void executeAction(ViewAction action);
}
