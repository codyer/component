/*
 * ************************************************************
 * 文件：NoCatCreatedException.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.exception;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class NoCatCreatedException extends RuntimeException {
    private static final long serialVersionUID = -4749478194441091368L;

    public NoCatCreatedException() {
        super("使用实例必须先调用createCat");
    }
}
