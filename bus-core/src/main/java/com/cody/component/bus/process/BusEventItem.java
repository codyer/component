/*
 * ************************************************************
 * 文件：BusProcessCache.java  模块：bus-core  项目：component
 * 当前修改时间：2020年06月12日 08:45:35
 * 上次修改时间：2020年06月12日 08:45:35
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bus-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.bus.process;

/**
 * 事件封装类
 */
class BusEventItem {
    public String scope;
    public String event;
    public String type;
    public String value;

    BusEventItem(final String scope, final String event, final String type, final String value) {
        this.scope = scope;
        this.event = event;
        this.type = type;
        this.value = value;
    }

    String getKey() {
        return scope + event + type;
    }
}
