/*
 * ************************************************************
 * 文件：PageListTestBindFragment.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 12:42:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;


import android.view.View;

import com.cody.component.R;
import com.cody.component.app.fragment.PageListBindFragment;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.handler.data.MaskViewData;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * test
 */
public class PageListTestBindFragment extends PageListBindFragment<ListTestViewModel> {


    public PageListTestBindFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public void onItemClick(final RecyclerView recyclerView, final View view, final int i, final long l) {
        getFriendlyViewModel().test();
    }

    @NonNull
    @Override
    public Class<ListTestViewModel> getVMClass() {
        return ListTestViewModel.class;
    }

    @Override
    public ListTestViewModel buildFriendlyViewModel() {
        return new ListTestViewModel(new MaskViewData());
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
