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

package com.cody.component.demo.list;


import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.cody.component.handler.data.MaskViewData;
import com.cody.component.handler.interfaces.OnRequestPageListener;
import com.cody.component.handler.mapper.PageDataMapper;
import com.cody.component.handler.viewmodel.PageListViewModel;

import java.util.ArrayList;

/**
 * Created by xu.yi. on 2019/4/14.
 * component
 */
public class ListTestViewModel extends PageListViewModel<MaskViewData, String> {

    @Override
    protected PageDataMapper<ItemTestViewData, String> createMapper() {
        return new PageDataMapper<ItemTestViewData, String>() {
            @NonNull
            @Override
            public ItemTestViewData createItem() {
                return new ItemTestViewData();
            }

            @Override
            public ItemTestViewData mapperItem(@NonNull final ItemTestViewData item, final String bean) {
                item.setTest(bean);
                return item;
            }
        };
    }

    @Override
    protected OnRequestPageListener<String> createRequestPageListener() {
        return (operation, pageInfo, callBack) -> new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> items = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    items.add(System.currentTimeMillis() + "item===" + (pageInfo.getPageNo() * pageInfo.getPageSize() + i));
                }
                if (pageInfo.getPageNo() == 3) {
                    items.clear();
                }
                pageInfo.setPageNo(pageInfo.getPageNo() + 1);
                callBack.onResult(items, null, pageInfo);
                onComplete(items);
            }
        }, 500);
    }

    public ListTestViewModel(final MaskViewData maskViewData) {
        super(maskViewData);
    }

    public void test() {
    }
}
