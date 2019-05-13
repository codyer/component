/*
 * ************************************************************
 * 文件：PageListViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.factory.PageListDataSourceFactory;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.mapper.IPageDataMapper;
import com.cody.component.handler.source.PageListKeyedDataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据
 */
@SuppressWarnings("unchecked")
public abstract class PageListViewModel<VD extends MaskViewData> extends FriendlyViewModel<VD> {
    private LiveData<PagedList<ItemViewDataHolder>> mPagedList;
    private MutableLiveData<PageListKeyedDataSource> mDataSource;
    private PagedList<ItemViewDataHolder> mOldList;

    protected abstract IPageDataMapper<?, ?> createMapper();

    protected abstract OnRequestPageListener<?> createRequestPageListener();
    public PageListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
        final PageListDataSourceFactory<Object> sourceFactory = new PageListDataSourceFactory<>((OnRequestPageListener) (operation, oldPageInfo, callBack) -> {
            if (mRequestStatus.isRefreshing()) {
                operation = Operation.REFRESH;
            }
            mRequestStatusLive.postValue(mRequestStatus = mRequestStatus.setOperation(operation));
            createRequestPageListener().onRequestPageData(operation, oldPageInfo, callBack);
        });
        mDataSource = sourceFactory.getDataSource();

        IPageDataMapper<ItemViewDataHolder, Object> mapper = (IPageDataMapper<ItemViewDataHolder, Object>) createMapper();

        // TODO 是否解决刷新闪问题？ 此处保留数据源？
        mPagedList = new LivePagedListBuilder<>(sourceFactory.map(mapper), initPageListConfig()).build();
    }

    /**
     * 分页数据配置
     */
    protected PagedList.Config initPageListConfig() {
        return (new PagedList.Config.Builder())
                .setPrefetchDistance(PageInfo.DEFAULT_PREFETCH_DISTANCE)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(PageInfo.DEFAULT_PAGE_SIZE)
                .setPageSize(PageInfo.DEFAULT_PAGE_SIZE)
                .build();
    }

    @Override
    public <T extends BaseViewModel> T setLifecycleOwner(final LifecycleOwner lifecycleOwner) {
        return super.setLifecycleOwner(lifecycleOwner);
    }

    public LiveData<PagedList<ItemViewDataHolder>> getPagedList() {
        return mPagedList;
    }

    /**
     * 执行一个操作
     */
    @Override
    protected void setOperation(RequestStatus requestStatus) {
        super.setOperation(requestStatus);

        if (requestStatus != null && mDataSource != null && mDataSource.getValue() != null) {
            if (requestStatus.isRefreshing()) {
                mOldList = mPagedList.getValue();
                mDataSource.getValue().refresh();
            } else if (requestStatus.isRetrying()) {
                mDataSource.getValue().retry();
            }
        }
    }
}