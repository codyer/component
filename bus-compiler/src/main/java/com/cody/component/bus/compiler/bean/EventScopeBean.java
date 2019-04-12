/*
 * ************************************************************
 * 文件：EventScopeBean.java  模块：bus-compiler  项目：component
 * 当前修改时间：2019年04月12日 09:21:20
 * 上次修改时间：2019年04月11日 09:54:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-compiler
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus.compiler.bean;

/**
 * Created by xu.yi. on 2019/4/2.
 * CleanFramework
 */
public class EventScopeBean {
    private String name;
    private boolean active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
