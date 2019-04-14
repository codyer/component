/*
 * ************************************************************
 * 文件：ItemTestViewData.java  模块：app  项目：component
 * 当前修改时间：2019年04月14日 16:01:57
 * 上次修改时间：2019年04月14日 16:01:57
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;

import com.cody.component.list.data.ItemMultiViewData;

/**
 * Created by xu.yi. on 2019/4/14.
 * component
 */
public class ItemTestViewData extends ItemMultiViewData {
    private static final long serialVersionUID = -9198367211217170445L;
    private String test;

    public ItemTestViewData(final String test) {
        this.test = test;
    }

    public String getTest() {
        return test;
    }

    public void setTest(final String test) {
        this.test = test;
    }
}
