/*
 * ************************************************************
 * 文件：OnRequestPageListener.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.listener;

import com.cody.component.list.define.PageInfo;
import com.cody.component.list.callback.PageDataCallBack;

/**
 * Created by xu.yi. on 2019/4/8.
 * 请求分页数据,可以通过数据库或者网络加载方式实现
 */
public interface OnRequestPageListener<ItemBean> {
    void OnRequestPageData(PageInfo oldPageInfo, PageDataCallBack<ItemBean> callBack);
}
