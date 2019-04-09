/*
 * ************************************************************
 * 文件：ListBean.java  模块：http-lib  项目：component
 * 当前修改时间：2019年04月07日 11:03:36
 * 上次修改时间：2019年03月29日 16:29:01
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.lib.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by xu.yi. on 2019/3/28.
 * 返回列表结果
 */
public class ListBean<T> {
    /**
     * 上次分页请求结果位置，默认-1
     */
    private String prePosition;
    /**
     * 上次分页请求结果位置，默认-1
     */
    @SerializedName(value = "nextPosition", alternate = {"nextPosition", "position"})
    private String nextPosition;
    /**
     * 列表请求结果
     */
    private List<T> items;

    public String getPrePosition() {
        return prePosition;
    }

    public void setPrePosition(String prePosition) {
        this.prePosition = prePosition;
    }

    public String getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(String position) {
        this.nextPosition = position;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "{" +
                "prePosition=" + prePosition +
                "nextPosition=" + nextPosition +
                ", items=" + items +
                '}';
    }
}