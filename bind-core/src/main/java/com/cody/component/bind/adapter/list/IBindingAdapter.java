/*
 * ************************************************************
 * 文件：IBindingAdapter.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 14:08:23
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-adapter
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bind.adapter.list;

import android.view.View;

/**
 * Created by xu.yi. on 2019/4/4.
 * binding adapter基类
 */
interface IBindingAdapter {
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
