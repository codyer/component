/*
 * ************************************************************
 * 文件：HtmlActivity.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月14日 00:14:46
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cody.component.app.activity.FragmentContainerWithCloseActivity;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.hybrid.OnUrlListener;
import com.cody.component.hybrid.R;
import com.cody.component.hybrid.core.UrlUtil;
import com.cody.component.hybrid.data.HtmlConfig;
import com.cody.component.hybrid.fragment.HtmlFragment;
import com.cody.component.util.ActivityUtil;
import com.umeng.socialize.UMShareAPI;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Html 页面
 */
public class HtmlActivity extends FragmentContainerWithCloseActivity implements OnUrlListener {
    public static final String HTML_WITH_CONFIG = "html_with_config";
    private HtmlFragment mHtmlFragment;
    private HtmlConfig mHtmlConfig;
    private Menu mMenu;
    private static Boolean isExit = false;
    private boolean mIsRoot = false;

    /**
     * 跳转html页面统一使用此函数
     *
     * @param title title为空意味着不要原生的头部
     * @param url   地址
     * @param root  根页面
     */
    public static void startHtml(String title, String description, String url, boolean share, boolean root) {
        HtmlConfig config = new HtmlConfig();
        config.setTitle(title).setDescription(description).setUrl(url).setCanShare(share).setRoot(root);
        startHtml(config);
    }

    /**
     * 跳转html页面统一使用此函数
     */
    public static void startHtml(HtmlConfig config) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(HTML_WITH_CONFIG, config);
        ActivityUtil.navigateTo(HtmlActivity.class, bundle);
    }

    /**
     * 跳转html页面统一使用此函数
     *
     * @param title title为空意味着不要原生的头部
     * @param url   地址
     */
    public static void startHtml(String title, String url) {
        startHtml(title, null, url, false, false);
    }

    /**
     * 跳转html页面统一使用此函数
     *
     * @param title title为空意味着不要原生的头部
     * @param url   地址
     */
    public static void startHtml(String title, String url, boolean share) {
        startHtml(title, null, url, share, false);
    }

    public void setHtmlConfig(@NonNull final HtmlConfig htmlConfig) {
        mHtmlConfig = htmlConfig;
        invalidateOptionsMenu();
    }

    @Override
    public Fragment getFragment() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mHtmlConfig = intent.getExtras().getParcelable(HTML_WITH_CONFIG);
            if (mHtmlConfig != null) {
                mIsRoot = mHtmlConfig.isRoot();
                mHtmlFragment = HtmlFragment.getInstance(mHtmlConfig.getUrl());
                if (getSupportActionBar() != null) {
                    setTitle(mHtmlConfig.getTitle());
                    getSupportActionBar().setSubtitle(mHtmlConfig.getDescription());
                    if (!TextUtils.isEmpty(mHtmlConfig.getUrl())) {
                        if (!UrlUtil.isInnerLink(mHtmlConfig.getUrl()) || !TextUtils.isEmpty(mHtmlConfig.getTitle())) {
                            //外链 或者内链且有title显示头部
                            getBinding().toolbar.setVisibility(View.VISIBLE);
                        } else {
                            getBinding().toolbar.setVisibility(View.GONE);
                        }
                    } else {
                        showToast(getString(R.string.ui_str_url_error));
                    }
                }
            }
        }
        return mHtmlFragment;
    }

    @Override
    public void onTitleChange(String title) {
        setTitle(title);
    }

    @Override
    public void onUrlChange(String url, boolean canGoBack) {
        if (mHtmlConfig != null) {
            mHtmlConfig.setUrl(url);
        }
        getQuickClose().setValue(canGoBack);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mHtmlFragment.onCancel(dialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        mHtmlFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JsBridge.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //添加点击返回箭头事件
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mHtmlFragment != null) {
            if (item.getItemId() == android.R.id.home) {
                if (mHtmlFragment.canGoBack()) {
                    return mHtmlFragment.goBack();
                }
            } else if (item.getItemId() == R.id.action_share) {
                if (mHtmlConfig != null) {
                    JsBridge.share(HtmlActivity.this, mHtmlConfig);
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mHtmlFragment != null && mHtmlFragment.canGoBack()) {
            mHtmlFragment.goBack();
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.html_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu) {
        mMenu = menu;
        if (null != mMenu) {
            if (mMenu.findItem(R.id.action_share) != null) {
                mMenu.findItem(R.id.action_share).setVisible(mHtmlConfig != null && mHtmlConfig.isCanShare());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    /**
     * 重写物理返回方法。如果html有上一页则跳转到html上一页，否则返回native
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mHtmlFragment != null) {
            if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
                if (mHtmlFragment.canGoBack()) {
                    return mHtmlFragment.goBack();
                } else if (mIsRoot) {
                    exitByDoubleClick(); //调用双击退出函数
                    return false;
                }
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitByDoubleClick() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            // 准备退出
            showToast(getString(R.string.ui_str_click_back_two_times));
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);
            // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
        }
    }

    @Override
    public void scrollToTop() {
        if (mHtmlFragment != null) {
            mHtmlFragment.scrollToTop();
        }
    }
}
