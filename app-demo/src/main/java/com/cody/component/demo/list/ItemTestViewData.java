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

package com.cody.component.demo.list;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.StringLiveData;

import java.util.Objects;

/**
 * Created by xu.yi. on 2019/4/14.
 * component
 */
public class ItemTestViewData extends ItemViewDataHolder {
    private final StringLiveData test = new StringLiveData("");
    private final BooleanLiveData mBooleanLiveData = new BooleanLiveData(false);
    private final StringLiveData mStringLiveData = new StringLiveData("111");

    public ItemTestViewData() {
    }

    public BooleanLiveData getBooleanLiveData() {
        return mBooleanLiveData;
    }

    public StringLiveData getStringLiveData() {
        return mStringLiveData;
    }

    public StringLiveData getTest() {
        return test;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ItemTestViewData viewData = (ItemTestViewData) o;
        return Objects.equals(test, viewData.test) &&
                Objects.equals(mBooleanLiveData, viewData.mBooleanLiveData) &&
                Objects.equals(mStringLiveData, viewData.mStringLiveData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), test, mBooleanLiveData, mStringLiveData);
    }
}
