/*
 * ************************************************************
 * 文件：MultiBindingListAdapter.java  模块：bind-core  项目：component
 * 当前修改时间：2019年05月23日 18:31:04
 * 上次修改时间：2019年05月22日 14:41:04
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bind.adapter.list;

import android.os.Handler;

import androidx.annotation.CallSuper;
import androidx.lifecycle.LifecycleOwner;

import com.cody.component.bind.R;
import com.cody.component.handler.data.ItemFooterOrHeaderData;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.interfaces.OnRetryListener;

/**
 * Created by xu.yi. on 2019/4/8.
 * 无分页 包含上拉加载更多，加载失败显示重试 footer header
 */
public abstract class MultiBindingListAdapter extends BindingListAdapter<ItemViewDataHolder> {
    final private ItemFooterOrHeaderData mItemHolderFooterOrHeader = new ItemFooterOrHeaderData();
    private RequestStatus mRequestStatus = new RequestStatus();
    private OnRetryListener mOnRetryListener;

    protected MultiBindingListAdapter(LifecycleOwner lifecycleOwner, OnRetryListener onRetryListener) {
        super(lifecycleOwner);
        mOnRetryListener = onRetryListener;
        setItemClickListener(null);
    }

    @Override
    final public void setItemClickListener(final OnBindingItemClickListener itemClickListener) {
        super.setItemClickListener((holder, view, position, id) -> {
            if (view.getId() == R.id.retryButton && mOnRetryListener != null) {
                mOnRetryListener.retry();
                notifyItemChanged(super.getItemCount());
            } else if (itemClickListener != null) {
                itemClickListener.onItemClick(holder, view, position, id);
            }
        });
    }

    /**
     * 设置是否显示没有更多了
     */
    final public void setShowFooter(boolean showFooter) {
        mItemHolderFooterOrHeader.setShowFooter(showFooter);
    }

    /**
     * 加载状态改变的时候记得通知adapter
     */
    final public void setRequestStatus(RequestStatus newState) {
        new Handler().post(() -> postRequestStatus(newState));
    }

    private void postRequestStatus(RequestStatus newState) {
        RequestStatus oldState = mRequestStatus;
        mItemHolderFooterOrHeader.setRequestStatus(newState);
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

    @CallSuper
    @Override
    public int getItemLayoutId(int viewType) {
        if (viewType == ItemFooterOrHeaderData.HEADER_OR_FOOTER_VIEW_TYPE) {
            return R.layout.item_load_more;
        }
        return -1;
    }

    @Override
    final public int getItemViewType(int position) {
        if ((hasFooterItem() && position == super.getItemCount()) || (hasHeaderItem() && position == 0)) {
            return ItemFooterOrHeaderData.HEADER_OR_FOOTER_VIEW_TYPE;
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
        return mRequestStatus.isLoadingBefore();
    }

    private boolean hasFooterItem() {
        return mRequestStatus.isLoadingAfter() || (super.getItemCount() > 0 && mRequestStatus.isError()) || mRequestStatus.isEnd();
    }
}
