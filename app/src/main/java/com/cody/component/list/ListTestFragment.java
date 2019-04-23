/*
 * ************************************************************
 * 文件：ListTestFragment.java  模块：app  项目：component
 * 当前修改时间：2019年04月14日 16:00:33
 * 上次修改时间：2019年04月14日 16:00:32
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list;


import android.app.Fragment;
import android.view.View;

import com.cody.component.R;
import com.cody.component.app.fragment.BindListFragment;
import com.cody.component.list.adapter.MultiBindingPageListAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListTestFragment extends BindListFragment {


    public ListTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(final View v) {

    }

    @Override
    public void onItemClick(final RecyclerView recyclerView, final View view, final int i, final long l) {

    }

    @NonNull
    @Override
    public Class<ListTestViewModel> getVMClass() {
        return ListTestViewModel.class;
    }

    @NonNull
    @Override
    public MultiBindingPageListAdapter getListAdapter() {
        return new MultiBindingPageListAdapter(this, this) {
            @Override
            public int getItemLayoutId(final int viewType) {
                if (viewType == 0){
                    return R.layout.item_test_list;
                }
                return super.getItemLayoutId(viewType);
            }
        };
    }
}
