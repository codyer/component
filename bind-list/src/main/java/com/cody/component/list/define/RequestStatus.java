/*
 * ************************************************************
 * 文件：RequestStatus.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月11日 14:31:11
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.define;

import android.text.TextUtils;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/4/8.
 * 请求数据的的状态
 */
public class RequestStatus {

    public static RequestStatus error(@NonNull String message) {
        return new RequestStatus(Status.FAILED, message);
    }

    public static RequestStatus loaded() {
        return new RequestStatus(Status.SUCCESS);
    }

    public static RequestStatus empty() {
        return new RequestStatus(Status.EMPTY);
    }

    public static RequestStatus loading() {
        return new RequestStatus(Status.RUNNING);
    }

    private Status mStatus;
    @NonNull
    private String mMessage;

    public RequestStatus(Status status) {
        mStatus = status;
    }

    public RequestStatus(Status status, @NonNull String message) {
        mStatus = status;
        mMessage = message;
    }

    @NonNull
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(@NonNull String message) {
        mMessage = message;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status status) {
        mStatus = status;
    }

    public boolean isLoading() {
        return mStatus == Status.RUNNING;
    }

    public boolean isError() {
        return mStatus == Status.FAILED;
    }

    public boolean isLoaded() {
        return mStatus == Status.SUCCESS;
    }

    public boolean isEmpty() {
        return mStatus == Status.EMPTY;
    }
}
