/*
 * ************************************************************
 * 文件：MultiListViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import com.cody.component.handler.BaseViewModel;
import com.cody.component.lib.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.factory.MultiDataSourceFactory;
import com.cody.component.handler.listener.OnListListener;
import com.cody.component.handler.listener.OnRequestPageListener;
import com.cody.component.handler.source.DataSourceWrapper;
import com.cody.component.handler.source.MultiPageKeyedDataSource;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据
 */
public abstract class MultiListViewModel<ItemBean> extends BaseViewModel
        implements OnRequestPageListener<ItemBean>, Function<ItemBean, ItemViewDataHolder>, OnListListener {
    private MaskViewData mMaskViewData = new MaskViewData();
    private MultiDataSourceFactory<ItemBean> mSourceFactory = new MultiDataSourceFactory<>(this, this);
    private DataSourceWrapper<ItemBean> mWrapper = new DataSourceWrapper<>(Transformations.switchMap(mSourceFactory.getDataSource(), MultiPageKeyedDataSource::getRequestStatus),
            Transformations.switchMap(mSourceFactory.getDataSource(), MultiPageKeyedDataSource::getOperation),
            mSourceFactory.getDataSource());

    private LiveData<PagedList<ItemViewDataHolder>> mPagedList = new LivePagedListBuilder<>(mSourceFactory.map(), initPageListConfig()).build();

    public MaskViewData getMaskViewData() {
        return mMaskViewData;
    }

    public LiveData<PagedList<ItemViewDataHolder>> getPagedList() {
        return mPagedList;
    }

    @Override
    public MutableLiveData<Operation> getOperation() {
        return mWrapper.getOperation();
    }

    @Override
    public MutableLiveData<RequestStatus> getRequestStatus() {
        return mWrapper.getRequestStatus();
    }

    @Override
    public void refresh() {
        mWrapper.refresh();
    }

    @Override
    public void retry() {
        mWrapper.retry();
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
}