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

/**
 * Created by xu.yi. on 2019-06-11.
 * 统一处理
 */
public interface IFriendlyView {
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
     * 网络无链接页面
     */
    default int noConnectionView() {
        return -1;
    }

    /**
     * 出错页面
     */
    default int errorView() {
        return -1;
    }
}
