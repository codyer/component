/*
 * ************************************************************
 * 文件：IBinding.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月14日 00:14:46
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.bind;

/**
 * Created by xu.yi. on 2019/3/25.
 * component
 */
public interface IBinding<B> {
    /**
     * 是否已经设置bind
     */
    boolean isBound();

    B getBinding();
}
