/*
 * ************************************************************
 * 文件：ListViewModel.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 13:39:51
 * 上次修改时间：2019年04月24日 13:37:30
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.viewmodel;

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.livedata.SafeMutableLiveData;
import com.cody.component.handler.mapper.IDataMapper;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

/**
 * Created by xu.yi. on 2019/4/8.
 * 获取列表数据
 */
public abstract class ListViewModel<VD extends MaskViewData> extends SingleViewModel<VD>
        implements IDataMapper {

    private MutableLiveData<List<ItemViewDataHolder>> mItems = new SafeMutableLiveData<>(new ArrayList<>());
    protected List<ItemViewDataHolder> mOldList = new ArrayList<>();

    public ListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
    }

    @Override
    public <T extends BaseViewModel> T setLifecycleOwner(final LifecycleOwner lifecycleOwner) {
        mItems.observe(lifecycleOwner, itemViewDataHolders -> mOldList = new ArrayList<>(itemViewDataHolders));
        return super.setLifecycleOwner(lifecycleOwner);
    }

    public MutableLiveData<List<ItemViewDataHolder>> getItems() {
        return mItems;
    }

    @Override
    public void onComplete() {
        super.onComplete();
        mItems.postValue(mOldList);
    }

    @Override
    public void onFailure(final String message) {
        super.onFailure(message);
    }

    @Override
    public <ItemBean> List<ItemViewDataHolder> mapperList(final Operation operation, final List<ItemBean> beanDataList) {
        mItems.postValue(mapperList(operation, mOldList, beanDataList));
        return mOldList;
    }
}