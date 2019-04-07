/*
 * ************************************************************
 * 文件：ListResult.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月07日 11:03:36
 * 上次修改时间：2019年03月29日 16:29:01
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.lib.bean;

/**
 * Created by xu.yi. on 2019/3/28.
 * ListResult
 */
public class ListResult<T> extends Result<ListBean<T>> {
    public ListResult() {
        super();
    }

    public ListResult(int code, String message, ListBean<T> data) {
        super(code, message, data);
    }

    @Override
    public String toString() {
        return "{" +
                "code='" + getCode() + '\'' +
                ", message='" + getMessage() + '\'' +
                ", data=" + getData().toString() +
                '}';
    }
}
