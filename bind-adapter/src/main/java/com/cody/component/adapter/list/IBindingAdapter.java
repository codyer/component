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

package com.cody.component.adapter.list;

import android.view.View;

import com.cody.component.lib.CoreBR;

/**
 * Created by xu.yi. on 2019/4/4.
 * binding adapter基类
 */
interface IBindingAdapter {
    /**
     * dataBinding 自动生成的BR文件对应的viewData id
     */
    default int getViewDataId() {
        return CoreBR.viewData;
    }

    /**
     * dataBinding 自动生成的BR文件对应的OnClickListener id
     */
    default int getOnClickListenerId() {
        return CoreBR.onClickListener;
    }

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
