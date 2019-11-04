/*
 * ************************************************************
 * 文件：CatMainActivity.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月23日 14:08:23
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cody.component.app.activity.AbsPageListActivity;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.bind.adapter.list.BindingViewHolder;
import com.cody.component.bind.adapter.list.MultiBindingPageListAdapter;
import com.cody.component.cat.HttpCat;
import com.cody.component.cat.R;
import com.cody.component.cat.databinding.CatActivityMainBinding;
import com.cody.component.cat.viewmodel.CatViewModel;
import com.cody.component.handler.data.FriendlyViewData;
import com.cody.component.handler.data.ItemViewDataHolder;

/**
 * Created by xu.yi. on 2019/3/26.
 * http 监视器
 */
public class CatMainActivity extends AbsPageListActivity<CatActivityMainBinding, CatViewModel> {

    @Override
    public FriendlyLayout getFriendlyLayout() {
        return getBinding().friendlyView;
    }

    @NonNull
    @Override
    public MultiBindingPageListAdapter buildListAdapter() {
        return new MultiBindingPageListAdapter(this, this) {
            @Override
            public int getItemLayoutId(int viewType) {
                if (viewType == ItemViewDataHolder.DEFAULT_TYPE) {
                    return R.layout.cat_item_main;
                }
                return super.getItemLayoutId(viewType);
            }
        };
    }

    @NonNull
    @Override
    public RecyclerView getRecyclerView() {
        return getBinding().recyclerView;
    }

    @Override
    protected FriendlyViewData getViewData() {
        return getViewModel().getFriendlyViewData();
    }

    @NonNull
    @Override
    public Class<CatViewModel> getVMClass() {
        return CatViewModel.class;
    }

    @Override
    public void onItemClick(final BindingViewHolder holder, final View view, final int position, final int id) {
        CatDetailsActivity.openActivity(CatMainActivity.this, getListAdapter().getItem(position));
    }

    @Override
    protected int getLayoutID() {
        return R.layout.cat_activity_main;
    }

    @Override
    public boolean isSupportImmersive() {
        return false;
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        if (!TextUtils.isEmpty(HttpCat.getInstance().getName())) {
            getBinding().toolbarTitle.setText(HttpCat.getInstance().getName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(HttpCat.getInstance().getName())) {
            getBinding().toolbarTitle.setText(HttpCat.getInstance().getName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cat_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cat_clear) {
            getViewModel(CatViewModel.class).clearAllCache();
            getViewModel(CatViewModel.class).clearNotification();
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.top) {
            getBinding().recyclerView.smoothScrollToPosition(0);
        }
    }
}
