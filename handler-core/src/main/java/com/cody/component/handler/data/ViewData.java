/*
 * ************************************************************
 * 文件：ViewData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:39:11
 * 上次修改时间：2019年04月23日 18:23:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

import android.os.Parcel;

/**
 * Created by xu.yi. on 2019/3/26.
 * 和界面绑定的数据基类默认实现
 */
public class ViewData implements IViewData {

    @Override
    public boolean areItemsTheSame(IViewData newData) {
        return this == newData;
    }

    @Override
    public boolean areContentsTheSame(IViewData newData) {
        return this.equals(newData);
    }

    public ViewData() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    protected ViewData(Parcel in) {
    }

    public static final Creator<ViewData> CREATOR = new Creator<ViewData>() {
        @Override
        public ViewData createFromParcel(Parcel source) {
            return new ViewData(source);
        }

        @Override
        public ViewData[] newArray(int size) {
            return new ViewData[size];
        }
    };
}
