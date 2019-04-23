/*
 * ************************************************************
 * 文件：IViewData.java  模块：lib-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月14日 21:38:23
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：lib-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.lib.data;

import java.io.Serializable;

/**
 * Created by xu.yi. on 2019/3/26.
 * 和界面绑定的数据基类接口
 */
public interface IViewData extends Serializable {
    boolean areItemsTheSame(IViewData newBind);

    boolean areContentsTheSame(IViewData newBind);
}
