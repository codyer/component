/*
 * ************************************************************
 * 文件：IView.java  模块：bind-base  项目：component
 * 当前修改时间：2019年04月05日 17:43:17
 * 上次修改时间：2019年04月05日 17:41:40
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.base.app;


import com.cody.component.base.handler.BaseViewModel;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/3/25.
 * base activity and base fragment 需要实现的接口
 */
public interface IView {
    <T extends BaseViewModel> T getViewModel(@NonNull Class<T> viewModelClass);

    /**
     * show loading message
     */
    void showLoading();

    /**
     * show loading message
     *
     * @param message 需要显示的消息：正在加载。。。
     */
    void showLoading(String message);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show toast message
     *
     * @param message 需要显示的消息：正在加载。。。
     */
    void showToast(String message);

    /**
     * 关闭当前activity
     */
    void finish();

    /**
     * 关闭当前activity，并设置返回结果
     */
    void finishWithResultOk();
}
