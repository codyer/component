/*
 * ************************************************************
 * 文件：OnRequestPageListener.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.interfaces;

import com.cody.component.handler.define.PageInfo;

/**
 * Created by xu.yi. on 2019/4/8.
 * 请求分页数据,可以通过数据库或者网络加载方式实现
 */
public interface OnRequestPageListener<ItemBean> {
    void OnRequestPageData(PageInfo oldPageInfo, PageDataCallBack<ItemBean> callBack);
}
