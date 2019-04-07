/*
 * ************************************************************
 * 文件：ScopeInactiveException.java  模块：core  项目：CleanFramework
 * 当前修改时间：2019年04月02日 13:56:43
 * 上次修改时间：2019年04月02日 13:36:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.lib.exception;

/**
 * Created by xu.yi. on 2019/3/31.
 * scope未激活
 */
public class ScopeInactiveException extends RuntimeException {
    private static final long serialVersionUID = -9041702601234976446L;

    public ScopeInactiveException() {
        super("使用的scope未激活，事件无法监听和发送");
    }
}
