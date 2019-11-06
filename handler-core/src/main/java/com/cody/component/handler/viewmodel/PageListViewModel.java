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
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.DataSource;

import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.factory.PageListDataSourceFactory;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.mapper.PageDataMapper;
import com.cody.component.handler.source.PageListKeyedDataSource;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据，分页获取
 */
public abstract class PageListViewModel<VD extends FriendlyViewData, Bean> extends AbsPageListViewModel<VD, PageInfo> {
    private MutableLiveData<PageListKeyedDataSource> mDataSource;
    private PageDataMapper<ItemViewDataHolder, Bean> mPageDataMapper;

    protected abstract PageDataMapper<? extends ItemViewDataHolder, Bean> createMapper();

    protected abstract OnRequestPageListener<Bean> createRequestPageListener();

    public PageListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected DataSource.Factory<PageInfo, ItemViewDataHolder> createDataSourceFactory() {
        mPageDataMapper = (PageDataMapper<ItemViewDataHolder, Bean>) createMapper();
        PageListDataSourceFactory<Bean> sourceFactory = new PageListDataSourceFactory<>(mPageDataMapper, (operation, oldPageInfo, callBack) -> {
            if (mRequestStatus.isRefreshing()) {
                operation = Operation.REFRESH;
            }
            mRequestStatusLive.postValue(mRequestStatus = mRequestStatus.setOperation(operation));
            createRequestPageListener().onRequestPageData(operation, oldPageInfo, callBack);
        });
        mDataSource = sourceFactory.getDataSource();
        return sourceFactory.map();
    }

    @Override
    public <T extends BaseViewModel> T setLifecycleOwner(final LifecycleOwner lifecycleOwner) {
        if (mLifecycleOwner == null && lifecycleOwner != null) {
            getPagedList().observe(lifecycleOwner, items -> getRequestStatusLive().observe(lifecycleOwner, new Observer<RequestStatus>() {
                @Override
                public void onChanged(final RequestStatus requestStatus) {
                    if (requestStatus.isLoaded()) {
                        getRequestStatusLive().removeObserver(this);
                        mPageDataMapper.setOldItems(items);
                    }
                }
            }));
        }
        return super.setLifecycleOwner(lifecycleOwner);
    }

    /**
     * 执行一个操作
     */
    @Override
    protected void startOperation(RequestStatus requestStatus) {
        super.startOperation(requestStatus);

        if (requestStatus != null && mDataSource != null && mDataSource.getValue() != null) {
            if (requestStatus.isInitializing()) {
                mDataSource.getValue().invalidate();
            } else if (requestStatus.isRefreshing()) {
                mDataSource.getValue().refresh();
            } else if (requestStatus.isRetrying()) {
                mDataSource.getValue().retry();
            }
        }
    }
}