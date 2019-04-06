/*
 * ************************************************************
 * 文件：MonitorOverviewFragment.java  模块：http-monitor  项目：component
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
import com.cody.http.cat.databinding.MonitorFragmentOverviewBinding;
import com.cody.http.cat.db.data.ItemMonitorData;
import com.cody.http.cat.viewmodel.MonitorViewModel;

/**
 * Created by xu.yi. on 2019/4/5.
 * MonitorOverviewFragment
 */
public class MonitorOverviewFragment extends SingleBindFragment<MonitorFragmentOverviewBinding, ItemMonitorData> {

    public static MonitorOverviewFragment newInstance() {
        return new MonitorOverviewFragment();
    }

    public MonitorOverviewFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.monitor_fragment_overview;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        getViewModel(MonitorViewModel.class).getRecordLiveData().observe(this, new Observer<ItemMonitorData>() {
            @Override
            public void onChanged(@Nullable ItemMonitorData monitorItemMonitorData) {
                if (monitorItemMonitorData != null) {
                    bindViewData();
                }
            }
        });
    }

    @Override
    protected ItemMonitorData getViewData() {
        return getViewModel(MonitorViewModel.class).getRecordLiveData().getValue();
    }
}