/*
 * ************************************************************
 * 文件：ViewData.java  模块：lib-core  项目：component
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
        return this.equals(newBind);
    }
}
