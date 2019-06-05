/*
 * ************************************************************
 * 文件：RequestFriendlyCallback.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月29日 10:55:50
 * 上次修改时间：2019年04月26日 22:46:37
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.http.callback;

/**
 * Created by xu.yi. on 2019/4/6.
 * 页面自己处理loading，无需框架处理
 */
public interface RequestFriendlyCallback<T> extends RequestMultiplyCallback<T> {

    @Override
    default boolean startWithLoading() {
        return false;
    }

    @Override
    default boolean endDismissLoading() {
        return false;
    }
}