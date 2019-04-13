/*
 * ************************************************************
 * 文件：IBindingAdapter.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-adapter
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter.list;

import android.view.View;

/**
 * Created by xu.yi. on 2019/4/4.
 * binding adapter基类
 */
interface IBindingAdapter {
    /**
     * dataBinding 自动生成的BR文件对应的viewData id
     */
    int getViewDataId();

    /**
     * dataBinding 自动生成的BR文件对应的OnClickListener id
     */
    int getOnClickListenerId();

    /**
     * 获取List中的Item的Layout ID
     *
     * @param viewType 支持多个itemType
     * @return LayoutId
     */
    int getItemLayoutId(int viewType);

    /**
     * 设置点击事件
     */
    void setItemClickListener(OnBindingItemClickListener itemClickListener);

    /**
     * 设置长按事件
     */
    void setItemLongClickListener(View.OnCreateContextMenuListener itemLongClickListener);
}
