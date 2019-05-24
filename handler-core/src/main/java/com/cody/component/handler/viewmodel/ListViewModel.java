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

import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.mapper.DataMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xu.yi. on 2019/4/8.
 * 获取列表数据
 */
public abstract class ListViewModel<VD extends MaskViewData, Item extends ItemViewDataHolder, Bean> extends SingleViewModel<VD> {

    private MutableLiveData<List<Item>> mItems = new MutableLiveData<>(new ArrayList<>());
    private List<Item> mOldList = new ArrayList<>();
    private DataMapper<Item, Bean> mDataMapper = createMapper();

    protected abstract DataMapper<Item, Bean> createMapper();

    public ListViewModel(final VD friendlyViewData) {
        super(friendlyViewData);
    }

    public MutableLiveData<List<Item>> getItems() {
        return mItems;
    }

    @Override
    public void onComplete(Object result) {
        super.onComplete(result);
        submitList();
    }

    public void submitList() {
        mItems.postValue(mOldList);
    }

    public void mapperList(final Operation operation, final List<Bean> beanList) {
        if (operation == Operation.REFRESH || operation == Operation.INIT) {
            mDataMapper.init();
        }
        mOldList = mDataMapper.mapperList(beanList);
    }
}