/*
 * ************************************************************
 * 文件：MultiBindingPageListAdapter.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:13:31
 * 上次修改时间：2019年04月09日 15:11:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.adapter;

import com.cody.component.adapter.list.BindingPageListAdapter;
import com.cody.component.adapter.list.OnBindingItemClickListener;
import com.cody.component.list.BR;
import com.cody.component.list.data.ItemMultiViewData;
import com.cody.component.list.R;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.exception.MissingOverrideBaseFunction;
import com.cody.component.list.listener.OnRetryListener;

import androidx.annotation.CallSuper;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by xu.yi. on 2019/4/8.
 * 包含下拉加载更多，加载失败显示重试 footer
 */
public abstract class MultiBindingPageListAdapter<VD extends ItemMultiViewData> extends BindingPageListAdapter<VD> implements OnRetryListener {
    final private static int FOOTER_VIEW_TYPE = -1;
    private RequestStatus mRequestStatus;

    protected MultiBindingPageListAdapter(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
        setItemClickListener(null);
    }

    @Override
   final public void setItemClickListener(final OnBindingItemClickListener itemClickListener) {
        super.setItemClickListener((parent, view, position, id) -> {
            if (view.getId() == R.id.retryButton) {
                retry();
            } else if (itemClickListener != null) {
                itemClickListener.onItemClick(parent, view, position, id);
            }
        });
    }

    /**
     * 加载状态改变的时候记得通知adapter
     */
   final public void setRequestStatus(RequestStatus newState) {
        RequestStatus oldState = mRequestStatus;
        boolean hadFooter = hasFooterItem();
        mRequestStatus = newState;
        boolean hasFooter = hasFooterItem();
        if (hadFooter != hasFooter) {
            if (hadFooter) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasFooter && oldState != newState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    @Override
    final public int getViewDataId() {
        return BR.viewData;
    }

    @Override
    final public int getOnClickListenerId() {
        return BR.onClickListener;
    }

    @CallSuper
    @Override
    final public int getItemLayoutId(int viewType) {
        if (viewType == FOOTER_VIEW_TYPE) {
            return R.layout.item_load_more_footer;
        }
        throw new MissingOverrideBaseFunction("getItemLayoutId");
    }

    @Override
    final public int getItemViewType(int position) {
        if (hasFooterItem() && position == getItemCount() - 1) {
            return FOOTER_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    final public int getItemCount() {
        return super.getItemCount() + (hasFooterItem() ? 1 : 0);
    }

    private boolean hasFooterItem() {
        return mRequestStatus != null && (mRequestStatus.isError() || mRequestStatus.isLoading());
    }
}
