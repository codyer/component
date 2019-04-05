/*
 * ************************************************************
 * 文件：MonitorMainActivity.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cody.component.adapter.list.BindingListAdapter;
import com.cody.component.adapter.list.OnBindingItemClickListener;
import com.cody.component.base.app.activity.EmptyBindActivity;
import com.cody.http.monitor.BR;
import com.cody.http.monitor.R;
import com.cody.http.monitor.databinding.MonitorActivityMainBinding;
import com.cody.http.monitor.db.data.ItemMonitorData;
import com.cody.http.monitor.viewmodel.MonitorViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by xu.yi. on 2019/3/26.
 * http 监视器
 */
public class MonitorMainActivity extends EmptyBindActivity<MonitorActivityMainBinding> {
    private BindingListAdapter<ItemMonitorData> mListAdapter = new BindingListAdapter<ItemMonitorData>(this) {

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
            return R.layout.monitor_item_main;
        }
    };

    @Override
    protected int getLayoutID() {
        return R.layout.monitor_activity_main;
    }

    @Override
    public void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        getBinding().recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter.setItemClickListener(new OnBindingItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                MonitorDetailsActivity.openActivity(MonitorMainActivity.this, id);
            }
        });
        getBinding().recyclerView.setAdapter(mListAdapter);
        getViewModel(MonitorViewModel.class).getAllRecordLiveData().observe(this, new Observer<List<ItemMonitorData>>() {
            @Override
            public void onChanged(@Nullable List<ItemMonitorData> itemMonitorDataList) {
                mListAdapter.submitList(itemMonitorDataList);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.monitor_menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.monitor_clear) {
            getViewModel(MonitorViewModel.class).clearAllCache();
            getViewModel(MonitorViewModel.class).clearNotification();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
