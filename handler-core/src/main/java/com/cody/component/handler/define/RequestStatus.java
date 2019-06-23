/*
 * ************************************************************
 * 文件：RequestStatus.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.define;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/4/8.
 * 请求数据的的状态
 */
public class RequestStatus {
    private Status mStatus;
    private String mMessage;
    private Operation mOperation;

    public RequestStatus() {
        mStatus = Status.RUNNING;
        mOperation = Operation.INIT;
    }

    private RequestStatus(Operation operation, Status status) {
        mOperation = operation;
        mStatus = status;
    }

    private RequestStatus(Operation operation, Status status, String message) {
        mOperation = operation;
        mStatus = status;
        mMessage = message;
    }

    public Operation getOperation() {
        return mOperation;
    }

    public RequestStatus setOperation(final Operation operation) {
        return new RequestStatus(operation, Status.RUNNING);
    }

    public RequestStatus refresh() {
        return setOperation(Operation.REFRESH);
    }

    public RequestStatus init() {
        return new RequestStatus();
    }

    public RequestStatus retry() {
        return setOperation(Operation.RETRY);
    }

    public RequestStatus loadAfter() {
        return setOperation(Operation.LOAD_AFTER);
    }

    public RequestStatus loadABefore() {
        return setOperation(Operation.LOAD_BEFORE);
    }

    public RequestStatus error(@NonNull String message) {
        return new RequestStatus(mOperation, Status.FAILED, message);
    }

    public RequestStatus loaded() {
        return new RequestStatus(mOperation, Status.SUCCESS);
    }

    public RequestStatus cancel() {
        return new RequestStatus(mOperation, Status.CANCEL);
    }

    public RequestStatus empty() {
        return new RequestStatus(mOperation, Status.EMPTY);
    }

    public RequestStatus end() {
        return new RequestStatus(mOperation, Status.END);
    }

    public RequestStatus loading() {
        return new RequestStatus(mOperation, Status.RUNNING);
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

    public boolean isSuccess() {
        return mStatus == Status.SUCCESS;
    }

    public boolean isLoaded() {
        return mStatus == Status.SUCCESS || mStatus == Status.EMPTY || mStatus == Status.END;
    }

    public boolean isEnd() {
        return mStatus == Status.END || isEmpty();
    }

    public boolean isEmpty() {
        return mStatus == Status.EMPTY;
    }

    public boolean isInitializing() {
        return mOperation == Operation.INIT && isLoading();
    }

    public boolean isRetrying() {
        return mOperation == Operation.RETRY && isLoading();
    }

    public boolean isRefreshing() {
        return mOperation == Operation.REFRESH && isLoading();
    }

    public boolean isLoadingBefore() {
        return mOperation == Operation.LOAD_BEFORE && isLoading();
    }

    public boolean isLoadingAfter() {
        return mOperation == Operation.LOAD_AFTER && isLoading();
    }
}
