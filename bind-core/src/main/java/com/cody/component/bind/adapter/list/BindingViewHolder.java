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

package com.cody.component.bind.adapter.list;

import android.view.View;
import android.widget.AdapterView;

import com.cody.component.bind.CoreBR;
import com.cody.component.handler.data.ItemViewDataHolder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xu.yi. on 2019/4/4.
 * component
 */
public class BindingViewHolder extends RecyclerView.ViewHolder {
    private View.OnCreateContextMenuListener mContextMenuListener;
    private OnBindingItemClickListener mClickListener;
    private RecyclerView mParentView;

    protected BindingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <B extends ViewDataBinding> B getItemBinding() {
        return DataBindingUtil.bind(itemView);
    }

    void bindTo(ItemViewDataHolder item, RecyclerView recyclerView, View.OnCreateContextMenuListener contextMenuListener, OnBindingItemClickListener clickListener) {
        mContextMenuListener = contextMenuListener;
        mClickListener = clickListener;
        mParentView = recyclerView;
        itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menuInfo = new AdapterView.AdapterContextMenuInfo(v, getAdapterPosition(), v.getId());
            if (mContextMenuListener != null) {
                mContextMenuListener.onCreateContextMenu(menu, v, menuInfo);
            }
        });
        getItemBinding().setVariable(CoreBR.viewData, item);
        //分发点击事件
        getItemBinding().setVariable(CoreBR.onClickListener, (View.OnClickListener) v -> {
            if (mClickListener != null) {
                mClickListener.onItemClick(mParentView, v, getAdapterPosition(), v.getId());
            }
        });
        getItemBinding().executePendingBindings();
    }

    void clear() {
        getItemBinding().setVariable(CoreBR.viewData, null);
        //分发点击事件
        getItemBinding().setVariable(CoreBR.onClickListener, null);
        mParentView = null;
        mContextMenuListener = null;
        mClickListener = null;
    }
}
