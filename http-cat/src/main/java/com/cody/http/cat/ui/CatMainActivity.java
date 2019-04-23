/*
 * ************************************************************
 * 文件：CatMainActivity.java  模块：http-cat  项目：component
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cody.component.adapter.list.BindingListAdapter;
import com.cody.component.app.activity.EmptyBindActivity;
import com.cody.http.cat.BR;
import com.cody.http.cat.R;
import com.cody.http.cat.databinding.CatActivityMainBinding;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.viewmodel.CatViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by xu.yi. on 2019/3/26.
 * http 监视器
 */
public class CatMainActivity extends EmptyBindActivity<CatActivityMainBinding> {
    private final BindingListAdapter mListAdapter = new BindingListAdapter(this) {

        @Override
        public int getViewDataId() {
            return BR.viewData;
        }

        @Override
        public int getOnClickListenerId() {
            return BR.onClickListener;
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.cat_item_main;
        }
    };

    @Override
    protected int getLayoutID() {
        return R.layout.cat_activity_main;
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter.setItemClickListener((parent, view, position, id) ->
                CatDetailsActivity.openActivity(CatMainActivity.this, (ItemHttpData) mListAdapter.getItem(position).getItemData()));
        getBinding().recyclerView.setAdapter(mListAdapter);
        getViewModel(CatViewModel.class).getAllRecordLiveData().observe(this, mListAdapter::submitList);
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
        if (v.getId() == R.id.top) {
            getBinding().recyclerView.smoothScrollToPosition(0);
        }
    }
}
