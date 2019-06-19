/*
 * ************************************************************
 * 文件：H5Exception.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年06月19日 11:38:13
 * 上次修改时间：2019年06月19日 11:38:13
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid;

import com.cody.component.lib.exception.BaseException;

/**
 * Created by xu.yi. on 2019-06-19.
 * component H5
 */
public class H5Exception extends BaseException {
    private static final long serialVersionUID = 2972956667521489395L;

    public H5Exception(final String message) {
        super(message);
    }
}
