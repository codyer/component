/*
 * ************************************************************
 * 文件：IBinding.java  模块：bind-base  项目：component
 * 当前修改时间：2019年04月05日 17:43:17
 * 上次修改时间：2019年04月05日 17:41:40
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app;

/**
 * Created by xu.yi. on 2019/3/25.
 * p-gbb-android
 */
public interface IBinding<B> {
    /**
     * 是否已经设置bind
     */
    boolean isBound();

    B getBinding();
}
