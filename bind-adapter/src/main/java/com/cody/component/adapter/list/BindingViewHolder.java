/*
 * ************************************************************
 * 文件：BindingViewHolder.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月04日 14:43:21
 * 上次修改时间：2019年04月04日 14:32:15
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter.list;

import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;

import com.cody.component.view.data.ItemViewData;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xu.yi. on 2019/4/4.
 * component
 */
public class BindingViewHolder<I extends ItemViewData> extends RecyclerView.ViewHolder {
    private int mViewDataId;
    private int mOnClickListenerId;
    private View.OnCreateContextMenuListener mContextMenuListener;
    private OnBindingItemClickListener mClickListener;
    private RecyclerView mParentView;

    BindingViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public <T extends ViewDataBinding> T getItemBinding() {
        return DataBindingUtil.bind(itemView);
    }

    void bindTo(I item, int viewDataId, int onClickListenerId, RecyclerView recyclerView, View.OnCreateContextMenuListener contextMenuListener, OnBindingItemClickListener clickListener) {
        mViewDataId = viewDataId;
        mOnClickListenerId = onClickListenerId;
        mContextMenuListener = contextMenuListener;
        mClickListener = clickListener;
        mParentView = recyclerView;
        itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menuInfo = new AdapterView.AdapterContextMenuInfo(v, getAdapterPosition(), v.getId());
                if (mContextMenuListener != null) {
                    mContextMenuListener.onCreateContextMenu(menu, v, menuInfo);
                }
            }
        });
        getItemBinding().setVariable(mViewDataId, item);
        //分发点击事件
        getItemBinding().setVariable(mOnClickListenerId, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onItemClick(mParentView, v, getAdapterPosition(), v.getId());
                }
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
