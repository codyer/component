/*
 * ************************************************************
 * 文件：ItemViewDataHolder.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:39:11
 * 上次修改时间：2019年04月23日 18:23:20
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

/**
 * Created by xu.yi. on 2019/3/28.
 * 和界面绑定的数据基类默认实现，并用于列表
 */
public final class ItemViewDataHolder<VD extends ViewData> extends ViewData {
    private static final long serialVersionUID = -6368977380223902277L;
    private int mItemId = 0;
    private int mItemType = 0;//不要为负数
    private VD mItemData;//真实数据

    public ItemViewDataHolder() {
    }

    public ItemViewDataHolder(final VD itemData) {
        mItemData = itemData;
    }

    public ItemViewDataHolder(final int itemType, final VD itemData) {
        mItemType = itemType;
        mItemData = itemData;
    }

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

    public VD getItemData() {
        return mItemData;
    }

    public void setItemData(final VD itemData) {
        mItemData = itemData;
    }

    @Override
    public boolean areItemsTheSame(final IViewData newData) {
        if (newData instanceof ItemViewDataHolder) {
            return mItemId == ((ItemViewDataHolder) newData).getItemId() && mItemType == ((ItemViewDataHolder) newData).getItemType();
        }
        return super.areItemsTheSame(newData);
    }

    @Override
    public boolean areContentsTheSame(final IViewData newData) {
        if (newData instanceof ItemViewDataHolder) {
            if (mItemData != null) {
                return this.mItemData.areItemsTheSame(((ItemViewDataHolder) newData).mItemData);
            }
        }
        return super.areContentsTheSame(newData);
    }
}
