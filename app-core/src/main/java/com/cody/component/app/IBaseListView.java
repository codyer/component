/*
 * ************************************************************
 * 文件：IBaseListView.java  模块：app-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app;

import androidx.annotation.NonNull;

import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.handler.interfaces.OnRetryListener;
import com.cody.component.handler.interfaces.Scrollable;

/**
 * Created by xu.yi. on 2019/4/10.
 * 列表绑定需要实现的接口
 */
public interface IBaseListView extends OnRetryListener, Scrollable {
    @NonNull
    MultiBindingPageListAdapter buildListAdapter();
}
