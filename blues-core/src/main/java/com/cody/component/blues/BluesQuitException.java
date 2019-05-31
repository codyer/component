/*
 * ************************************************************
 * 文件：BluesQuitException.java  模块：cody-component  项目：component
 * 当前修改时间：2019年05月31日 18:46:49
 * 上次修改时间：2018年12月07日 16:22:12
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：cody-component
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.blues;

/**
 * Created by cody.yi on 2018/6/6.
 * blues 退出异常处理类
 */
final public class BluesQuitException extends RuntimeException {
    public BluesQuitException(String message) {
        super(message);
    }

    public BluesQuitException(Throwable cause) {
        super(cause);
    }
}
