/*
 * ************************************************************
 * 文件：OnListListener.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月11日 15:06:52
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.listener;

import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.define.Operation;
import com.cody.component.list.define.RequestStatus;


/**
 * Created by xu.yi. on 2019/4/9.
 * 列表通用监听
 */
public interface OnListListener extends OnRefreshListener, OnRetryListener {
    SafeMutableLiveData<Operation> getOperation();

    SafeMutableLiveData<RequestStatus> getRequestStatus();
}
