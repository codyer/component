/*
 * ************************************************************
 * 文件：ListTestViewModel.java  模块：app  项目：component
 * 当前修改时间：2019年04月14日 16:01:13
 * 上次修改时间：2019年04月14日 16:01:13
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;

import com.cody.component.list.callback.PageDataCallBack;
import com.cody.component.list.define.PageInfo;
import com.cody.component.list.viewmodel.MultiListViewModel;

import java.util.ArrayList;

/**
 * Created by xu.yi. on 2019/4/14.
 * component
 */
public class ListTestViewModel extends MultiListViewModel<ItemTestViewData, String> {
    @Override
    public ItemTestViewData apply(final String input) {
        return new ItemTestViewData(input);
    }

    @Override
    public void OnRequestPageData(final PageInfo pageInfo, final PageDataCallBack<String> pageDataCallBack) {
        ArrayList<String> items = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            items.add("item===" + i);
        }
        if (pageInfo.getPageNo() == 4) {
            items.clear();
        }
        pageInfo.setPageNo(pageInfo.getPageNo() + 1);
        pageDataCallBack.onSuccess(items, null, pageInfo);
    }
}
