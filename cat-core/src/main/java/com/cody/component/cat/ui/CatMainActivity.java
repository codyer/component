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

import com.cody.component.bind.adapter.list.BindingListAdapter;
import com.cody.component.app.activity.EmptyBindActivity;
import com.cody.component.cat.HttpCat;
import com.cody.component.cat.R;
import com.cody.component.cat.databinding.CatActivityMainBinding;
import com.cody.component.cat.db.data.ItemHttpData;
import com.cody.component.cat.viewmodel.CatViewModel;

import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * Created by xu.yi. on 2019/3/26.
 * http 监视器
 */
public class CatMainActivity extends EmptyBindActivity<CatActivityMainBinding> {
    private final BindingListAdapter<ItemHttpData> mListAdapter = new BindingListAdapter<ItemHttpData>(this) {
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
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter.setItemClickListener((parent, view, position, id) ->
                CatDetailsActivity.openActivity(CatMainActivity.this, mListAdapter.getItem(position)));
        getBinding().recyclerView.setAdapter(mListAdapter);
        getViewModel(CatViewModel.class).getAllRecordLiveData().observe(this, mListAdapter::submitList);
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
        if (v.getId() == R.id.top) {
            getBinding().recyclerView.smoothScrollToPosition(0);
        }
    }
}
