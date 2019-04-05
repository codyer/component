/*
 * ************************************************************
 * 文件：SimpleBean.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:46:39
 * 上次修改时间：2019年04月05日 23:29:14
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.bean;


/**
 * Created by cody.yi on 2016/7/12.
 * 网络只返回简单的code和message的bean
 */
public class SimpleBean extends Result<Object> {

    public SimpleBean(String code, String message) {
        super(code, message, null);
    }
}
