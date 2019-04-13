/*
 * ************************************************************
 * 文件：CatOverviewFragment.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.ui;

import android.os.Bundle;

import android.view.View;

import com.cody.component.app.fragment.SingleBindFragment;
import com.cody.http.cat.R;
import com.cody.http.cat.databinding.CatFragmentOverviewBinding;
import com.cody.http.cat.db.data.ItemHttpData;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatOverviewFragment
 */
public class CatOverviewFragment extends SingleBindFragment<CatFragmentOverviewBinding, ItemHttpData> {
    private static final String ITEM_VIEW_DATA = "itemHttpData";
    private ItemHttpData mItemHttpData;

    public static CatOverviewFragment newInstance(ItemHttpData itemHttpData) {
        CatOverviewFragment fragment = new CatOverviewFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEM_VIEW_DATA, itemHttpData);
        fragment.setArguments(bundle);
        return fragment;
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
    protected ItemHttpData getViewData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mItemHttpData = (ItemHttpData) bundle.getSerializable(ITEM_VIEW_DATA);
        }
        return mItemHttpData;
    }
}