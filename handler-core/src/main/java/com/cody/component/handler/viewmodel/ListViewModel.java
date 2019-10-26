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

import androidx.lifecycle.MutableLiveData;

import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.mapper.DataMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu.yi. on 2019/4/8.
 * 获取列表数据 不需要分页
 */
public abstract class ListViewModel<VD extends FriendlyViewData, Bean> extends SingleViewModel<VD> {

    private MutableLiveData<List<ItemViewDataHolder>> mItems = new MutableLiveData<>(new ArrayList<>());
    private DataMapper<ItemViewDataHolder, Bean> mDataMapper;

    protected abstract DataMapper<? extends ItemViewDataHolder, Bean> createMapper();

    @SuppressWarnings("unchecked")
    public ListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
        mDataMapper = (DataMapper<ItemViewDataHolder, Bean>) createMapper();
    }

    public MutableLiveData<List<ItemViewDataHolder>> getItems() {
        return mItems;
    }

    /**
     * mapper 结束数据就会更新到list中
     */
    public void mapperListAppend(final Operation operation, final List<Bean> beanList) {
        if (operation == Operation.REFRESH || operation == Operation.INIT) {
            mDataMapper.init();
        }
        mItems.postValue(mDataMapper.mapperListAppend(beanList));
    }
}