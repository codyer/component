/*
 * ************************************************************
 * 文件：OnListListener.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:14:45
 * 上次修改时间：2019年04月09日 15:11:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.listener;

import com.cody.component.list.define.Operation;
import com.cody.component.list.define.RequestStatus;

import androidx.lifecycle.LiveData;

/**
 * Created by xu.yi. on 2019/4/9.
 * 列表通用监听
 */
public interface OnListListener extends OnRefreshListener, OnRetryListener {
    LiveData<Operation> getOperation();

    LiveData<RequestStatus> getRequestStatus();
}
