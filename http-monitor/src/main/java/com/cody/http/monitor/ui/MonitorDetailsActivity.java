/*
 * ************************************************************
 * 文件：MonitorDetailsActivity.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor.ui;

import androidx.lifecycle.Observer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.cody.component.base.app.activity.EmptyBindActivity;
import com.cody.http.monitor.databinding.MonitorActivityDetailsBinding;
import com.cody.http.monitor.db.data.ItemMonitorData;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.cody.http.monitor.R;
import com.cody.http.monitor.utils.FormatUtils;
import com.cody.http.monitor.viewmodel.MonitorViewModel;

/**
 * Created by xu.yi. on 2019/4/5.
 * MonitorDetailsActivity
 */
public class MonitorDetailsActivity extends EmptyBindActivity<MonitorActivityDetailsBinding> {
    private static final String KEY_ID = "keyId";
    private ItemMonitorData mItemMonitorData;

    public static void openActivity(Context context, long id) {
        Intent intent = new Intent(context, MonitorDetailsActivity.class);
        intent.putExtra(KEY_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.monitor_activity_details;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initView();
        long id = getIntent().getLongExtra(KEY_ID, 0);
        getViewModel(MonitorViewModel.class).queryRecordById(id);
        getViewModel(MonitorViewModel.class).getRecordLiveData().observe(this, new Observer<ItemMonitorData>() {
            @Override
            public void onChanged(@Nullable ItemMonitorData itemMonitorData) {
                MonitorDetailsActivity.this.mItemMonitorData = itemMonitorData;
                if (itemMonitorData != null) {
                    getBinding().toolbarTitle.setText(String.format("%s  %s", itemMonitorData.getMethod(), itemMonitorData.getPath()));
                } else {
                    getBinding().toolbarTitle.setText(null);
                }
            }
        });
    }

    private void initView() {
        PagerAdapter fragmentPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        fragmentPagerAdapter.addFragment(MonitorOverviewFragment.newInstance(), "overview");
        fragmentPagerAdapter.addFragment(MonitorPayloadFragment.newInstanceRequest(), "request");
        fragmentPagerAdapter.addFragment(MonitorPayloadFragment.newInstanceResponse(), "response");
        getBinding().viewPager.setAdapter(fragmentPagerAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.monitor_menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.monitor_share) {
            if (mItemMonitorData != null) {
                share(FormatUtils.getShareText(mItemMonitorData));
            }
        }
        return true;
    }

    private void share(String content) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, null));
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();

        private List<String> mTabs = new ArrayList<>();

        private PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTabs.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTabs.get(position);
        }
    }
}