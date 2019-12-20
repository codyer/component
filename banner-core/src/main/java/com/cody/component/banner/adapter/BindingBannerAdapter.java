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

import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.LifecycleOwner;

import com.cody.component.banner.R;
import com.cody.component.banner.data.BannerViewData;
import com.cody.component.bind.adapter.list.BindingListAdapter;
import com.cody.component.bind.adapter.list.OnBindingItemClickListener;

/**
 * Created by cody.yi on 2017/4/26.
 * RecyclerView适配器，假无限循环列表
 */
public class BindingBannerAdapter<T extends BannerViewData> extends BindingListAdapter<T> {

    public BindingBannerAdapter(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    public int getItemLayoutId(int viewType) {
        return R.layout.default_binding_banner;
    }

    @Override
    public void setItemClickListener(final OnBindingItemClickListener itemClickListener) {
        super.setItemClickListener((holder, view, position, id) -> {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(holder, view, getPosition(position), id);
            }
        });
    }

    @Override
    public void setItemLongClickListener(final View.OnCreateContextMenuListener itemLongClickListener) {
        super.setItemLongClickListener((menu, v, menuInfo) -> {
            if (itemLongClickListener != null) {
                if (menuInfo instanceof AdapterView.AdapterContextMenuInfo) {
                    ((AdapterView.AdapterContextMenuInfo) menuInfo).position = getPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
                    itemLongClickListener.onCreateContextMenu(menu, v, menuInfo);
                }
            }
        });
    }

    @Override
    public T getItem(int position) {
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

    public int getPosition(int position) {
        if (getBannerSize() == 0)return 0;
        return position % getBannerSize();
    }
}
