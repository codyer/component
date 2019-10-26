/*
 * ************************************************************
 * 文件：PageListTestFragment.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 12:42:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo.list;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.fragment.PageListFragment;
import com.cody.component.bind.adapter.list.BindingViewHolder;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.demo.R;
import com.cody.component.handler.data.FriendlyViewData;

/**
 * test
 */
public class PageListTestFragment extends PageListFragment<TestPageListViewModel> {


    public PageListTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(final View v) {
        super.onClick(v);
    }

    @Override
    public void onItemClick(BindingViewHolder holder, final View view, final int position, final int id) {
        if (id == R.id.testButton) {
            ItemTestViewData item = (ItemTestViewData) getListAdapter().getItem(position);
            if (item != null) {
                item.getStringLiveData().setValue(System.currentTimeMillis() + "");
            }
        }
        getViewModel().test();
    }

    @Override
    protected FriendlyViewData getViewData() {
        return getViewModel().getFriendlyViewData();
    }

    @NonNull
    @Override
    public Class<TestPageListViewModel> getVMClass() {
        return TestPageListViewModel.class;
    }

    @NonNull
    @Override
    public MultiBindingPageListAdapter buildListAdapter() {
        return new MultiBindingPageListAdapter(this, this) {
            @Override
            public int getItemLayoutId(final int viewType) {
                if (viewType == 0) {
                    return R.layout.item_test_list;
                }
                return super.getItemLayoutId(viewType);
            }
        };
    }
}
