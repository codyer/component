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

import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.data.ViewData;
import com.cody.component.handler.interfaces.PageDataCallBack;
import com.cody.component.handler.define.PageInfo;
import com.cody.component.handler.viewmodel.PageListViewModel;
import com.cody.component.handler.data.ItemViewDataHolder;

import java.util.ArrayList;
import java.util.List;

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
    public <ItemBean> ViewData mapper(final ViewData viewData, final ItemBean beanData, final int position) {
        if (viewData instanceof ItemTestViewData) {
            ((ItemTestViewData) viewData).setTest((String) beanData);
        }
        return viewData;
    }

    public void test() {
    }

    @Override
    public void OnRequestPageData(final PageInfo pageInfo, final PageDataCallBack pageDataCallBack) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add("item===" + i);
        }
        if (pageInfo.getPageNo() == 3) {
            items.clear();
        }
        pageInfo.setPageNo(pageInfo.getPageNo() + 1);
        pageDataCallBack.onSuccess(mapperList(items), null, pageInfo);
    }

    @Override
    public ViewData newItemViewData() {
        return new ItemTestViewData();
    }
}
