/*
 * ************************************************************
 * 文件：CatDetailsActivity.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cody.component.app.activity.EmptyBindActivity;
import com.cody.component.cat.R;
import com.cody.component.cat.databinding.CatActivityDetailsBinding;
import com.cody.component.cat.db.data.ItemHttpData;
import com.cody.component.cat.utils.FormatUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatDetailsActivity
 */
public class CatDetailsActivity extends EmptyBindActivity<CatActivityDetailsBinding> {
    private static final String ITEM_HTTP_DATA = "itemHttpData";
    private ItemHttpData mItemHttpData;

    public static void openActivity(Context context, ItemHttpData itemHttpData) {
        Intent intent = new Intent(context, CatDetailsActivity.class);
        intent.putExtra(ITEM_HTTP_DATA, itemHttpData);
        context.startActivity(intent);
    }

    @Override
    public boolean isSupportImmersive() {
        return false;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.cat_activity_details;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        setSupportActionBar(getBinding().toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initView();
    }

    private void initView() {
        mItemHttpData = getIntent().getParcelableExtra(ITEM_HTTP_DATA);
        PagerAdapter fragmentPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        fragmentPagerAdapter.addFragment(CatOverviewFragment.newInstance(mItemHttpData), "overview");
        fragmentPagerAdapter.addFragment(CatPayloadFragment.newInstanceRequest(mItemHttpData), "request");
        fragmentPagerAdapter.addFragment(CatPayloadFragment.newInstanceResponse(mItemHttpData), "response");
        getBinding().viewPager.setAdapter(fragmentPagerAdapter);
        getBinding().tabLayout.setupWithViewPager(getBinding().viewPager);

        if (mItemHttpData != null) {
            getBinding().toolbarTitle.setText(String.format("%s  %s", mItemHttpData.getMethod(), mItemHttpData.getPath()));
        } else {
            getBinding().toolbarTitle.setText(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cat_menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.cat_share) {
            if (mItemHttpData != null) {
                share(FormatUtils.getShareText(mItemHttpData));
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

        private final List<Fragment> mFragments = new ArrayList<>();

        private final List<String> mTabs = new ArrayList<>();

        private PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTabs.add(title);
        }

        @NonNull
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