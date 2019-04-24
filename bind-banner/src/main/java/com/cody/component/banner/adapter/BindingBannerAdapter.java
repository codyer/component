/*
 * ************************************************************
 * 文件：BindingBannerAdapter.java  模块：bind-banner  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 14:08:23
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-banner
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.banner.adapter;

import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;

import com.cody.component.bind.adapter.list.BindingListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;
import com.cody.component.banner.R;
import com.cody.component.handler.data.ItemViewDataHolder;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by cody.yi on 2017/4/26.
 * RecyclerView适配器，假无限循环列表
 */
public class BindingBannerAdapter extends BindingListAdapter {

    public BindingBannerAdapter(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    public int getItemLayoutId(int viewType) {
        return R.layout.default_binding_banner;
    }

    @Override
    public void setItemClickListener(final OnBindingItemClickListener itemClickListener) {
        super.setItemClickListener(new OnBindingItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(parent, view, getPosition(position), id);
                }
            }
        });
    }

    @Override
    public void setItemLongClickListener(final View.OnCreateContextMenuListener itemLongClickListener) {
        super.setItemLongClickListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                if (itemLongClickListener != null) {
                    if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
                        ((AdapterView.AdapterContextMenuInfo) menuInfo).position = getPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
                        itemLongClickListener.onCreateContextMenu(menu, v, menuInfo);
                    }
                }
            }
        });
    }

    @Override
    public ItemViewDataHolder<?> getItem(int position) {
        return super.getItem(getPosition(position));
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(getPosition(position));
    }

    public int getBannerSize() {
        return super.getItemCount();
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() < 2 ? super.getItemCount() : Integer.MAX_VALUE;
    }

    private int getPosition(int position) {
        return position % getBannerSize();
    }
}
