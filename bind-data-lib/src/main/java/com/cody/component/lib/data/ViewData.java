/*
 * ************************************************************
 * 文件：ViewData.java  模块：view-bind  项目：component
 * 当前修改时间：2019年04月04日 14:31:30
 * 上次修改时间：2019年04月04日 13:48:27
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.data;

/**
 * Created by xu.yi. on 2019/3/26.
 * 和界面绑定的数据基类默认实现
 */
public class ViewData implements IViewData {
    private static final long serialVersionUID = 998314704089921211L;

    @Override
    public boolean areItemsTheSame(IViewData newBind) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(IViewData newBind) {
        return false;
    }
}
