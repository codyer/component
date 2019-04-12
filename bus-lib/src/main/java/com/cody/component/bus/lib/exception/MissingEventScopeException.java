/*
 * ************************************************************
 * 文件：MissingEventScopeException.java  模块：bus-lib  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-lib
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.lib.exception;

/**
 * Created by xu.yi. on 2019/3/31.
 * 事件枚举类没有使用EventScope注解
 */
public class MissingEventScopeException extends RuntimeException {
    private static final long serialVersionUID = 4265334833804926157L;

    public MissingEventScopeException() {
        super("事件枚举类没有使用EventScope注解");
    }
}
