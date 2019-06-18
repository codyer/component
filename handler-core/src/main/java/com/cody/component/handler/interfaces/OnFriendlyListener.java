/*
 * ************************************************************
 * 文件：OnFriendlyListener.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.cody.component.handler.define.RequestStatus;


/**
 * Created by xu.yi. on 2019/4/9.
 * 用户友好 通用监听
 */
public interface OnFriendlyListener extends Refreshable, OnRetryListener, OnInitListener {
    @NonNull
    RequestStatus getRequestStatus();

    @NonNull
    MutableLiveData<RequestStatus> getRequestStatusLive();

    void refreshUI(RequestStatus status);

    void onFailure(final String message);

    void onCancel();
}
