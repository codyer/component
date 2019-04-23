/*
 * ************************************************************
 * 文件：BindingViewHolder.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 11:17:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-adapter
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter.list;

import android.view.View;
import android.widget.AdapterView;

import com.cody.component.lib.data.ItemViewDataHolder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xu.yi. on 2019/4/4.
 * component
 */
public class BindingViewHolder extends RecyclerView.ViewHolder {
    private int mViewDataId;
    private int mOnClickListenerId;
    private View.OnCreateContextMenuListener mContextMenuListener;
    private OnBindingItemClickListener mClickListener;
    private RecyclerView mParentView;

    protected BindingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <B extends ViewDataBinding> B getItemBinding() {
        return DataBindingUtil.bind(itemView);
    }

    void bindTo(ItemViewDataHolder item, int viewDataId, int onClickListenerId, RecyclerView recyclerView, View.OnCreateContextMenuListener contextMenuListener, OnBindingItemClickListener clickListener) {
        mViewDataId = viewDataId;
        mOnClickListenerId = onClickListenerId;
        mContextMenuListener = contextMenuListener;
        mClickListener = clickListener;
        mParentView = recyclerView;
        itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menuInfo = new AdapterView.AdapterContextMenuInfo(v, getAdapterPosition(), v.getId());
            if (mContextMenuListener != null) {
                mContextMenuListener.onCreateContextMenu(menu, v, menuInfo);
            }
        });
        getItemBinding().setVariable(mViewDataId, item.getItemData());
        //分发点击事件
        getItemBinding().setVariable(mOnClickListenerId, (View.OnClickListener) v -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(mParentView, v, getAdapterPosition(), v.getId());
            }
        });
        getItemBinding().executePendingBindings();
    }

    void clear() {
        getItemBinding().setVariable(mViewDataId, null);
        //分发点击事件
        getItemBinding().setVariable(mOnClickListenerId, null);
        mParentView = null;
        mContextMenuListener = null;
        mClickListener = null;
    }
}
