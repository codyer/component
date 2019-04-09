/*
 * ************************************************************
 * 文件：RequestStatus.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:24:57
 * 上次修改时间：2019年04月09日 11:54:43
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.define;

/**
 * Created by xu.yi. on 2019/4/8.
 * 请求数据的的状态
 */
public class RequestStatus {

    public static RequestStatus error(String message) {
        return new RequestStatus(Status.FAILED, message);
    }

    public static RequestStatus loaded() {
        return new RequestStatus(Status.SUCCESS);
    }

    public static RequestStatus loading() {
        return new RequestStatus(Status.RUNNING);
    }

    private Status mStatus;
    private String mMessage;

    public RequestStatus(Status status) {
        mStatus = status;
    }

    public RequestStatus(Status status, String message) {
        mStatus = status;
        mMessage = message;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
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
}
