/*
 * ************************************************************
 * 文件：ItemViewData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月29日 01:55:42
 * 上次修改时间：2019年04月29日 01:55:42
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
public class ItemViewData extends ItemViewDataHolder {
    private static final long serialVersionUID = -1457260658295437405L;
    final private BooleanLiveData mValid = new BooleanLiveData(true);//是否有效，可以控制显示不显示

    public ItemViewData() {
    }

    public BooleanLiveData getValid() {
        return mValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemViewData)) return false;

        ItemViewData that = (ItemViewData) o;

        return mValid.getValue() != null ? mValid.getValue().equals(that.mValid.getValue()) : that.mValid.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (mValid.getValue() != null ? mValid.getValue().hashCode() : 0);
        return result;
    }

    @Override
    public boolean areItemsTheSame(final IViewData newData) {
        if (newData instanceof ItemViewData) {
            return super.areItemsTheSame(newData) &&
                    mValid.get() == ((ItemViewData) newData).getValid().get();
        }
        return super.areItemsTheSame(newData);
    }

    @Override
    public boolean areContentsTheSame(final IViewData newData) {
        return this.equals(newData);
    }
}
