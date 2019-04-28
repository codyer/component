/*
 * ************************************************************
 * 文件：ListBean.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.bean;

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
    private int prePosition;
    /**
     * 上次分页请求结果位置，默认-1
     */
    @SerializedName(value = "nextPosition", alternate = {"position"})
    private int nextPosition;
    /**
     * 列表请求结果
     */
    private List<T> items;

    public int getPrePosition() {
        return prePosition;
    }

    public void setPrePosition(int prePosition) {
        this.prePosition = prePosition;
    }

    public int getNextPosition() {
        return nextPosition;
    }

    public void setNextPosition(int position) {
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