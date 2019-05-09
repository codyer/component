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

import android.os.Parcel;

/**
 * Created by xu.yi. on 2019/3/28.
 * 和界面绑定的数据基类默认实现，并用于列表
 */
public class ItemViewDataHolder extends ViewData {
    protected int mItemId = 0;
    protected int mItemType = 0;//不要为负数

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

    @Override
    public int hashCode() {
        int result = mItemId;
        result = 31 * result + mItemType;
        return result;
    }

    @Override
    public boolean areItemsTheSame(final IViewData newData) {
        if (newData instanceof ItemViewDataHolder) {
            return mItemId == ((ItemViewDataHolder) newData).getItemId() &&
                    mItemType == ((ItemViewDataHolder) newData).getItemType();
        }
        return super.areItemsTheSame(newData);
    }

    @Override
    public boolean areContentsTheSame(final IViewData newData) {
        return this.equals(newData);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mItemId);
        dest.writeInt(this.mItemType);
    }

    protected ItemViewDataHolder(Parcel in) {
        super(in);
        this.mItemId = in.readInt();
        this.mItemType = in.readInt();
    }

}
