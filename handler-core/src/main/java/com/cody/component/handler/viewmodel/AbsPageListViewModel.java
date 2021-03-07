/*
 * ************************************************************
 * 文件：AbsPageListViewModel.java  模块：component.handler-core  项目：component
 * 当前修改时间：2021年03月07日 17:23:38
 * 上次修改时间：2021年03月07日 17:22:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：component.handler-core
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.PageInfo;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，支持数据库 获取列表数据，分页获取
 */
public abstract class AbsPageListViewModel<VD extends FriendlyViewData, Key> extends FriendlyViewModel<VD> {
    private LiveData<PagedList<ItemViewDataHolder>> mPagedList;

    protected abstract DataSource.Factory<Key, ItemViewDataHolder> createDataSourceFactory();

    public AbsPageListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
    }

    /**
     * 分页数据配置
     */
    protected PagedList.Config initPageListConfig() {
        return new PagedList.Config.Builder()
                .setPrefetchDistance(PageInfo.DEFAULT_PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PageInfo.DEFAULT_PAGE_SIZE)
                .setPageSize(PageInfo.DEFAULT_PAGE_SIZE)
                .build();
    }

    public LiveData<PagedList<ItemViewDataHolder>> getPagedList() {
        if (mPagedList == null) {
            if (isInitOnce()) {
                mPagedList = new LivePagedListBuilder<>(createDataSourceFactory(), initPageListConfig())
                        .setBoundaryCallback(new PagedList.BoundaryCallback<ItemViewDataHolder>() {
                            @Override
                            public void onZeroItemsLoaded() {
                                super.onZeroItemsLoaded();
                                submitStatus(mRequestStatus.empty());
                            }

                            @Override
                            public void onItemAtFrontLoaded(@NonNull final ItemViewDataHolder itemAtFront) {
                                super.onItemAtFrontLoaded(itemAtFront);
                                submitStatus(mRequestStatus.end());
                            }

                            @Override
                            public void onItemAtEndLoaded(@NonNull final ItemViewDataHolder itemAtEnd) {
                                super.onItemAtEndLoaded(itemAtEnd);
                                submitStatus(mRequestStatus.end());
                            }
                        }).build();
            } else {
                mPagedList = new LivePagedListBuilder<>(createDataSourceFactory(), initPageListConfig()).build();
            }
        }
        return mPagedList;
    }
}