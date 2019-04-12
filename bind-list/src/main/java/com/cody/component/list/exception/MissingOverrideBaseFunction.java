/*
 * ************************************************************
 * 文件：MissingOverrideBaseFunction.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月09日 15:11:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.exception;

/**
 * Created by xu.yi. on 2019/4/8.
 * component
 */
public class MissingOverrideBaseFunction extends RuntimeException {
    private static final long serialVersionUID = 2753104152530264309L;

    public MissingOverrideBaseFunction(String name) {
        super("没有重载方法父类的方法：" + name);
    }
}
