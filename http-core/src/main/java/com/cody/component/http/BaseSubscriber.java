/*
 * ************************************************************
 * 文件：BaseSubscriber.java  模块：http-core  项目：component
 * 当前修改时间：2019年06月05日 13:59:03
 * 上次修改时间：2019年05月30日 21:30:40
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http;

import com.cody.component.http.callback.RequestCallback;
import com.cody.component.http.callback.RequestMultiplyCallback;
import com.cody.component.http.lib.config.HttpCode;
import com.cody.component.http.lib.exception.TokenInvalidException;
import com.cody.component.http.lib.exception.base.BaseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public class BaseSubscriber<T> extends DisposableObserver<T> {

    private final RequestCallback<T> requestCallback;

    BaseSubscriber(RequestCallback<T> requestCallback) {
        this.requestCallback = requestCallback;
    }

    @Override
    public void onNext(T t) {
        if (requestCallback != null && !isDisposed()) {
            requestCallback.onSuccess(t);
            dispose();
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (requestCallback instanceof RequestMultiplyCallback) {
            RequestMultiplyCallback callback = (RequestMultiplyCallback) requestCallback;
            if (e instanceof BaseException) {
                callback.onFail((BaseException) e);
            } else if (e instanceof HttpException) {
                if (((HttpException) e).code() == HttpCode.CODE_TOKEN_INVALID) {
                    callback.onFail(new TokenInvalidException());
                } else {
                    callback.onFail(new BaseException(((HttpException) e).code(), e.getMessage()));
                }
            } else {
                callback.onFail(new BaseException(HttpCode.CODE_UNKNOWN, e.getMessage()));
            }
        } else {
            if (requestCallback != null) {
                requestCallback.showToast(e.getMessage());
            }
        }
    }

    @Override
    public void onComplete() {
        if (requestCallback != null && !isDisposed()) {
            requestCallback.onSuccess(null);
            dispose();
        }
    }
}