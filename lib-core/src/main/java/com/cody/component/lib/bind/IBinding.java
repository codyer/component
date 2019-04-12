/*
 * ************************************************************
 * 文件：IBinding.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:20
 * 上次修改时间：2019年04月10日 15:58:25
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.bind;

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
