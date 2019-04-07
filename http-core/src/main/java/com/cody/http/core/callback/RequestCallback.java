/*
 * ************************************************************
 * 文件：RequestCallback.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月06日 01:58:30
 * 上次修改时间：2019年03月25日 09:23:00
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.callback;

import com.cody.http.core.holder.ToastHolder;

/**
 * Created by xu.yi. on 2019/4/6.
 */
public interface RequestCallback<T> {

    void onSuccess(T t);

    default boolean startWithLoading() {
        return true;
    }

    default boolean endDismissLoading() {
        return true;
    }

    default void showToast(String message) {
        ToastHolder.showToast(message);
    }
}
