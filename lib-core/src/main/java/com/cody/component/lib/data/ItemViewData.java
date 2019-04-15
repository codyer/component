/*
 * ************************************************************
 * 文件：ItemViewData.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.data;

/**
 * Created by xu.yi. on 2019/3/28.
 * 和界面绑定的数据基类默认实现，并用于列表
 */
public class ItemViewData extends ViewData {
    private static final long serialVersionUID = -6368977380223902277L;
    private int mItemId = 0;
    private int mItemType = 0;//不要为负数

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(final int itemId) {
        mItemId = itemId;
    }

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        mItemType = itemType;
    }

    @Override
    public boolean areItemsTheSame(final IViewData newBind) {
        if (newBind instanceof ItemViewData) {
            return mItemId == ((ItemViewData) newBind).getItemId();
        }
        return super.areItemsTheSame(newBind);
    }

    @Override
    public boolean areContentsTheSame(final IViewData newBind) {
        return this.equals(newBind);
    }
}
