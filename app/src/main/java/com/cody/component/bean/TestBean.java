/*
 * ************************************************************
 * 文件：TestBean.java  模块：app  项目：component
 * 当前修改时间：2019年04月12日 09:21:11
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bean;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/4/3.
 * LiveEventBus
 */
public class TestBean {
    private String name;
    private String code;

    @NonNull
    @Override
    public String toString() {
        String sb = "TestDataBean{" + "name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
        return sb;
    }

    public TestBean(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
