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

import com.cody.component.handler.livedata.BooleanLiveData;

/**
 * Created by xu.yi. on 2019/3/28.
 * 和界面绑定的数据基类默认实现，并用于列表
 */
public class ItemViewDataHolder extends ViewData {
    private static final long serialVersionUID = -6368977380223902277L;
    private int mItemId = 0;
    private int mItemType = 0;//不要为负数
    final private BooleanLiveData mValid = new BooleanLiveData(true);//是否有效，可以控制显示不显示

    public ItemViewDataHolder() {
    }

    public ItemViewDataHolder(final int itemType) {
        mItemType = itemType;
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

    public BooleanLiveData getValid() {
        return mValid;
    }

    @Override
    public int hashCode() {
        int result = mItemId;
        result = 31 * result + mItemType;
        result = 31 * result + (mValid.getValue() != null ? mValid.getValue().hashCode() : 0);
        return result;
    }

    @Override
    public boolean areItemsTheSame(final IViewData newData) {
        if (newData instanceof ItemViewDataHolder) {
            return mItemId == ((ItemViewDataHolder) newData).getItemId() &&
                    mItemType == ((ItemViewDataHolder) newData).getItemType() &&
                    mValid.get() == ((ItemViewDataHolder) newData).getValid().get();
        }
        return super.areItemsTheSame(newData);
    }

    @Override
    public boolean areContentsTheSame(final IViewData newData) {
        return this.equals(newData);
    }
}
