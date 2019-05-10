/*
 * ************************************************************
 * 文件：ListTestViewModel.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 12:42:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;


import com.cody.component.handler.data.ItemViewDataHolder;
import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.interfaces.PageResultCallBack;
import com.cody.component.handler.viewmodel.PageListViewModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;

/**
 * Created by xu.yi. on 2019/4/14.
 * component
 */
public class ListTestViewModel extends PageListViewModel<MaskViewData> {
    public ListTestViewModel(final MaskViewData maskViewData) {
        super(maskViewData);
    }

    @Override
    public <ItemBean> ItemViewDataHolder mapperItem(@NonNull ItemViewDataHolder itemViewDataHolder, ItemBean beanData, int position) {
        if (itemViewDataHolder instanceof ItemTestViewData) {
         ((ItemTestViewData)itemViewDataHolder).setTest((String) beanData);
        }
        return itemViewDataHolder;
    }

    public void test() {
    }

    @Override
    public void onRequestPageData(Operation operation, final PageInfo pageInfo, final PageResultCallBack result) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add("item===" + (pageInfo.getPageNo() * pageInfo.getPageSize() + i));
        }
        if (pageInfo.getPageNo() == 3) {
            items.clear();
        }
        pageInfo.setPageNo(pageInfo.getPageNo() + 1);
        result.onResult(mapperList(operation, items), null, pageInfo);
        onComplete(items);
    }

    @Override
    public ItemTestViewData newItemViewData(int position) {
        return new ItemTestViewData();
    }
}
