/*
 * ************************************************************
 * 文件：BindingListAdapter.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月04日 14:43:21
 * 上次修改时间：2019年04月04日 14:32:15
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cody.component.view.data.ItemViewData;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xu.yi. on 2019/3/28.
 * 抽象列表adapter
 */
public abstract class BindingListAdapter<VD extends ItemViewData> extends ListAdapter<VD, BindingViewHolder<VD>> implements IBindingAdapter {

    private RecyclerView mRecyclerView;
    private OnBindingItemClickListener mItemClickListener;//item 事件监听
    private View.OnCreateContextMenuListener mItemLongClickListener;//item 长按事件监听
    private final LifecycleOwner mLifecycleOwner;

    @Override
    public void setItemClickListener(OnBindingItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    @Override
    public void setItemLongClickListener(View.OnCreateContextMenuListener itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    protected BindingListAdapter(LifecycleOwner lifecycleOwner) {
        super(new BindingItemDiffCallback<>());
        mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = null;
    }

    @Override
    public VD getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getItemViewType(int position) {
        VD item = getItem(position);
        if (item != null) {
            return item.getItemType();
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public BindingViewHolder<VD> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayoutId(viewType), parent, false);
        binding.setLifecycleOwner(mLifecycleOwner);
        return new BindingViewHolder<>(binding.getRoot());
    }

    @CallSuper
    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder<VD> holder, int position) {
        VD item = getItem(position);
        if (item != null) {
            holder.bindTo(item, getViewDataId(), getOnClickListenerId(), mRecyclerView, mItemLongClickListener, mItemClickListener);
        } else {
            holder.clear();
        }
    }
}
