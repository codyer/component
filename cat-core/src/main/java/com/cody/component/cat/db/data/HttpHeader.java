/*
 * ************************************************************
 * 文件：HttpHeader.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:54
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.db.data;

/**
 * Created by xu.yi. on 2019/3/31.
 * http header
 */
public class HttpHeader {

    private String name;

    private String value;

    public HttpHeader() {
    }

    HttpHeader(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
