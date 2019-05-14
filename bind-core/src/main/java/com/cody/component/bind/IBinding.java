/*
 * ************************************************************
 * 文件：IBinding.java  模块：bind-core  项目：component
 * 当前修改时间：2019年04月24日 09:42:41
 * 上次修改时间：2019年04月23日 18:23:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bind;

/**
 * Created by xu.yi. on 2019/3/25.
 * component
 */
public interface IBinding<B> {
    /**
     * 是否已经设置bind
     */
    boolean unBound();

    B getBinding();
}
