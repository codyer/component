/*
 * ************************************************************
 * 文件：MultiListViewModel.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月09日 15:14:13
 * 上次修改时间：2019年04月09日 15:11:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.viewmodel;

import com.cody.component.handler.BaseViewModel;
import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.data.MaskViewData;
import com.cody.component.list.factory.MultiDataSourceFactory;
import com.cody.component.list.source.DataSourceWrapper;
import com.cody.component.list.listener.OnListListener;
import com.cody.component.list.listener.OnRequestPageListener;
import com.cody.component.list.define.Operation;
import com.cody.component.list.define.PageInfo;
import com.cody.component.list.define.RequestStatus;
import com.cody.component.list.data.ItemMultiViewData;
import com.cody.component.list.exception.ParameterNullPointerException;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

/**
 * Created by xu.yi. on 2019/4/8.
 * 数据仓库，获取列表数据
 */
public abstract class MultiListViewModel<IVD extends ItemMultiViewData, ItemBean> extends BaseViewModel
        implements OnRequestPageListener<ItemBean>, Function<ItemBean, IVD>, OnListListener {
    private MaskViewData mMaskViewData = new MaskViewData();
    private DataSourceWrapper<ItemBean> mWrapper;
    private LiveData<PagedList<IVD>> mPagedList = new LivePagedListBuilder<>(initSourceFactory(), initPageListConfig()).build();

    public MaskViewData getMaskViewData() {
        return mMaskViewData;
    }

    public LiveData<PagedList<IVD>> getPagedList() {
        return mPagedList;
    }

    @Override
    public SafeMutableLiveData<Operation> getOperation() {
        if (mWrapper != null) {
            return mWrapper.getOperation();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }

    @Override
    public SafeMutableLiveData<RequestStatus> getRequestStatus() {
        if (mWrapper != null) {
            return mWrapper.getRequestStatus();
        }
        throw new ParameterNullPointerException("DataSourceWrapper");
    }

    @Override
    public void refresh() {
        if (mWrapper != null) {
            mWrapper.refresh();
        }
    }

    @Override
    public void retry() {
        if (mWrapper != null) {
            mWrapper.retry();
        }
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

    private DataSource.Factory<PageInfo, IVD> initSourceFactory() {
        MultiDataSourceFactory<IVD, ItemBean> factory = new MultiDataSourceFactory<>(this, this);
        mWrapper = new DataSourceWrapper<>(factory.getDataSource());
        return factory.map();
    }
}