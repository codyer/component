/*
 * ************************************************************
 * 文件：ItemViewData.java  模块：view-bind  项目：component
 * 当前修改时间：2019年04月04日 14:31:30
 * 上次修改时间：2019年04月04日 13:48:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.view.data;

/**
 * Created by xu.yi. on 2019/3/28.
 * 和界面绑定的数据基类默认实现，并用于列表
 */
public class ItemViewData extends ViewData {
    private int mItemType = 0;

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        mItemType = itemType;
    }
}
