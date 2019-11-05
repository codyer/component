/*
 * ************************************************************
 * 文件：NotInitDataBaseException.java  模块：cat-core  项目：component
 * 当前修改时间：2019年11月04日 16:03:37
 * 上次修改时间：2019年06月19日 11:30:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：cat-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.exception;

import com.cody.component.lib.exception.BaseException;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class NotInitDataBaseException extends BaseException {
    private static final long serialVersionUID = -4749478194441091368L;

    public NotInitDataBaseException() {
        super("使用实例必须先调用init");
    }
}
