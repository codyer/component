/*
 * ************************************************************
 * 文件：IView.java  模块：bind-data-lib  项目：component
 * 当前修改时间：2019年04月10日 14:34:29
 * 上次修改时间：2019年04月10日 14:26:59
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.view;


/**
 * Created by xu.yi. on 2019/3/25.
 * base activity and base fragment 需要实现的接口
 */
public interface IView {
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
