/*
 * ************************************************************
 * 文件：CatOverviewFragment.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月05日 18:42:55
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.ui;

import androidx.lifecycle.Observer;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.View;

import com.cody.component.app.fragment.SingleBindFragment;
import com.cody.http.cat.R;
import com.cody.http.cat.databinding.CatFragmentOverviewBinding;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.viewmodel.CatViewModel;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatOverviewFragment
 */
public class CatOverviewFragment extends SingleBindFragment<CatFragmentOverviewBinding, ItemHttpData> {

    public static CatOverviewFragment newInstance() {
        return new CatOverviewFragment();
    }

    public CatOverviewFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.cat_fragment_overview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        getViewModel(CatViewModel.class).getRecordLiveData().observe(this, new Observer<ItemHttpData>() {
            @Override
            public void onChanged(@Nullable ItemHttpData catItemHttpData) {
                if (catItemHttpData != null) {
                    bindViewData();
                }
            }
        });
    }

    @Override
    protected ItemHttpData getViewData() {
        return getViewModel(CatViewModel.class).getRecordLiveData().getValue();
    }
}