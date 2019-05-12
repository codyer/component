/*
 * ************************************************************
 * 文件：ItemTestViewData.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 12:52:48
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;

import com.cody.component.handler.data.ItemViewDataHolder;

import java.util.Objects;

/**
 * Created by xu.yi. on 2019/4/14.
 * component
 */
public class ItemTestViewData extends ItemViewDataHolder {
    private String test;

    public ItemTestViewData() {
    }

    public ItemTestViewData(final String test) {
        this.test = test;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ItemTestViewData that = (ItemTestViewData) o;
        return Objects.equals(test, that.test);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), test);
    }

    public String getTest() {
        return test;
    }

    public void setTest(final String test) {
        this.test = test;
    }
}
