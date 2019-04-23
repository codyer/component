/*
 * ************************************************************
 * 文件：MultiBindingPageListAdapter.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.adapter;

import com.cody.component.adapter.list.BindingPageListAdapter;
import com.cody.component.adapter.list.OnBindingItemClickListener;
import com.cody.component.lib.data.ItemViewDataHolder;
import com.cody.component.list.BR;
import com.cody.component.list.R;
import com.cody.component.list.data.ItemFooterOrHeaderData;
import com.cody.component.list.define.Operation;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.listener.OnRetryListener;

import androidx.annotation.CallSuper;
import androidx.lifecycle.LifecycleOwner;

/**
 * Created by xu.yi. on 2019/4/8.
 * 包含下拉加载更多，加载失败显示重试 header
 * 包含上拉加载更多，加载失败显示重试 footer
 */
public abstract class MultiBindingPageListAdapter extends BindingPageListAdapter {
    final private static int HEADER_OR_FOOTER_VIEW_TYPE = -1;
    final private ItemViewDataHolder mItemHolderFooterOrHeader = new ItemViewDataHolder(HEADER_OR_FOOTER_VIEW_TYPE, new ItemFooterOrHeaderData());

    private Operation mOperation;
    private RequestStatus mRequestStatus;
    private OnRetryListener mOnRetryListener;

    protected MultiBindingPageListAdapter(LifecycleOwner lifecycleOwner, OnRetryListener onRetryListener) {
        super(lifecycleOwner);
        mOnRetryListener = onRetryListener;
        setItemClickListener(null);
    }

    @Override
    final public void setItemClickListener(final OnBindingItemClickListener itemClickListener) {
        super.setItemClickListener((parent, view, position, id) -> {
            if (view.getId() == R.id.retryButton && mOnRetryListener != null) {
                mOnRetryListener.retry();
                notifyItemChanged(getItemCount() - 1);
            } else if (itemClickListener != null) {
                itemClickListener.onItemClick(parent, view, position, id);
            }
        });
    }

    /**
     * 设置是否显示没有更多了
     */
    final public void setShowFooter(boolean showFooter) {
        if (mItemHolderFooterOrHeader.getItemData() != null && mItemHolderFooterOrHeader.getItemData() instanceof ItemFooterOrHeaderData) {
            ((ItemFooterOrHeaderData) mItemHolderFooterOrHeader.getItemData()).setShowFooter(showFooter);
        }
    }

    /**
     * 操作改变通知adapter
     */
    final public void setOperation(Operation operation) {
        mOperation = operation;
    }

    /**
     * 加载状态改变的时候记得通知adapter
     */
    final public void setRequestStatus(RequestStatus newState) {
        RequestStatus oldState = mRequestStatus;
        if (mItemHolderFooterOrHeader.getItemData() instanceof ItemFooterOrHeaderData) {
            ((ItemFooterOrHeaderData) mItemHolderFooterOrHeader.getItemData()).setRequestStatus(newState);
        }
        boolean hadHeader = hasHeaderItem();
        boolean hadFooter = hasFooterItem();
        mRequestStatus = newState;
        boolean hasHeader = hasHeaderItem();
        boolean hasFooter = hasFooterItem();

        if (hadHeader != hasHeader) {
            if (hadHeader) {
                notifyItemRemoved(0);
            } else {
                notifyItemInserted(0);
            }
        } else if (hasHeader && oldState != newState) {
            notifyItemChanged(0);
        }

        if (hadFooter != hasFooter) {
            if (hadFooter) {
                notifyItemRemoved(super.getItemCount());
            } else {
                notifyItemInserted(super.getItemCount());
            }
        } else if (hasFooter && oldState != newState) {
            notifyItemChanged(super.getItemCount());
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
    public int getItemLayoutId(int viewType) {
        if (viewType == HEADER_OR_FOOTER_VIEW_TYPE) {
            return R.layout.item_load_more;
        }
        return -1;
    }

    @Override
    final public int getItemViewType(int position) {
        if ((hasFooterItem() && position == super.getItemCount()) || (hasHeaderItem() && position == 0)) {
            return HEADER_OR_FOOTER_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public ItemViewDataHolder getItem(final int position) {
        if ((hasFooterItem() && position == super.getItemCount()) || (hasHeaderItem() && position == 0)) {
            return mItemHolderFooterOrHeader;
        }
        return super.getItem(position);
    }

    @Override
    final public int getItemCount() {
        return super.getItemCount() + (hasHeaderOrFooterItem() ? 1 : 0);
    }

    private boolean hasHeaderOrFooterItem() {
        return hasHeaderItem() || hasFooterItem();
    }

    private boolean hasHeaderItem() {
        return (mOperation == Operation.LOAD_BEFORE) && mRequestStatus != null && (!mRequestStatus.isLoaded());
    }

    private boolean hasFooterItem() {
        return (mOperation == Operation.LOAD_AFTER) && mRequestStatus != null && (!mRequestStatus.isLoaded());
    }
}
