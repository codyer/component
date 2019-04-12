/*
 * ************************************************************
 * 文件：HttpHeader.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.db.data;

/**
 * Created by xu.yi. on 2019/3/31.
 * http header
 */
public class HttpHeader {

    private final String mName;

    private final String mValue;

    HttpHeader(String name, String value) {
        this.mName = name;
        this.mValue = value;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }

}
