/*
 * ************************************************************
 * 文件：AbsPageListViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年11月04日 14:15:44
 * 上次修改时间：2019年11月04日 14:11:02
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.PageInfo;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，支持数据库 获取列表数据，分页获取
 */
public abstract class AbsPageListViewModel<VD extends FriendlyViewData, Key> extends FriendlyViewModel<VD> {
    private LiveData<PagedList<ItemViewDataHolder>> mPagedList;
    protected abstract DataSource.Factory<Key, ItemViewDataHolder> createDataSourceFactory();

    public AbsPageListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
        mPagedList = new LivePagedListBuilder<>(createDataSourceFactory(), initPageListConfig()).build();
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
        return mPagedList;
    }
}