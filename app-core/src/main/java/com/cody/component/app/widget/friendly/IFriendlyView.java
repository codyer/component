/*
 * ************************************************************
 * 文件：IFriendlyView.java  模块：app-core  项目：component
 * 当前修改时间：2019年06月11日 16:59:36
 * 上次修改时间：2019年06月11日 16:59:36
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.widget.friendly;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import com.cody.component.handler.interfaces.Refreshable;

/**
 * Created by xu.yi. on 2019-06-11.
 * 统一处理
 */
public interface IFriendlyView extends Refreshable {
    /**
     * 初始化页面
     */
    default int initView() {
        return -1;
    }

    /**
     * 空页面
     */
    default int emptyView() {
        return -1;
    }

    /**
     * 出错页面
     */
    default int errorView() {
        return -1;
    }

    /**
     * 刷新页面
     */
    default void refresh() {
    }

    /**
     * Check if this view can be scrolled vertically in a certain direction.
     *
     * @param direction Negative to check scrolling up, positive to check scrolling down.
     * @return true if this view can be scrolled in the specified direction, false otherwise.
     */
    default boolean canScrollVertically(View target, int direction) {
        return target.canScrollVertically(direction);
    }

    /**
     * 必须有 LifecycleOwner 否则数据绑定失效
     */
    LifecycleOwner getLifecycleOwner();
}
