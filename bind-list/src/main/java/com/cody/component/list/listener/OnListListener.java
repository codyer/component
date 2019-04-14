/*
 * ************************************************************
 * 文件：OnListListener.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.listener;

import com.cody.component.list.define.Operation;
import com.cody.component.list.define.RequestStatus;

import androidx.lifecycle.MutableLiveData;


/**
 * Created by xu.yi. on 2019/4/9.
 * 列表通用监听
 */
public interface OnListListener extends OnRefreshListener, OnRetryListener {
    MutableLiveData<Operation> getOperation();

    MutableLiveData<RequestStatus> getRequestStatus();
}
