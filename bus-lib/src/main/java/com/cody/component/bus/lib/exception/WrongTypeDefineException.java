/*
 * ************************************************************
 * 文件：WrongTypeDefineException.java  模块：bus-lib  项目：component
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
 * 在同一scope存在相同的event定义，或者这个event已经被定义
 *
 */
public class WrongTypeDefineException extends RuntimeException {
    private static final long serialVersionUID = -5035036959348844480L;

    public WrongTypeDefineException() {
        super("请使用枚举类进行事件定义");
    }
}
