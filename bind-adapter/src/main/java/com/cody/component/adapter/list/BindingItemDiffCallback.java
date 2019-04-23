/*
 * ************************************************************
 * 文件：BindingItemDiffCallback.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月13日 08:43:55
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-adapter
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter.list;

import com.cody.component.lib.data.ItemViewDataHolder;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

/**
 * Created by xu.yi. on 2019/4/4.
 * component
 */
public class BindingItemDiffCallback extends DiffUtil.ItemCallback<ItemViewDataHolder> {
    @Override
    public boolean areItemsTheSame(@NonNull ItemViewDataHolder oldItem, @NonNull ItemViewDataHolder newItem) {
        return oldItem.areItemsTheSame(newItem);
    }

    @Override
    public boolean areContentsTheSame(@NonNull ItemViewDataHolder oldItem, @NonNull ItemViewDataHolder newItem) {
        return oldItem.areContentsTheSame(newItem);
    }
}