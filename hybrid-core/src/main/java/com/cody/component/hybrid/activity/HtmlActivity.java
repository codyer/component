/*
 * ************************************************************
 * 文件：HtmlActivity.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月11日 21:53:07
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
import android.view.KeyEvent;
import android.view.View;

import com.cody.component.app.activity.FragmentContainerActivity;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.hybrid.R;
import com.cody.component.hybrid.data.HtmlConfig;
import com.cody.component.hybrid.fragment.HtmlFragment;
import com.cody.component.util.ActivityUtil;

import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;

/**
 * Html 页面
 */
public class HtmlActivity extends FragmentContainerActivity {
    public static final String HTML_WITH_CONFIG = "html_with_config";
    private HtmlFragment mHtmlFragment;
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
        bundle.putSerializable(HTML_WITH_CONFIG, config);
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

    @Override
    public Fragment getFragment() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            HtmlConfig config = (HtmlConfig) intent.getExtras().getSerializable(HTML_WITH_CONFIG);
            if (config != null) {
                mIsRoot = config.isRoot();
                mHtmlFragment = HtmlFragment.getInstance(config);
            }
        }
        return mHtmlFragment;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        mHtmlFragment.onCancel(dialog);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mHtmlFragment.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 重写物理返回方法。如果html有上一页则跳转到html上一页，否则返回native
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (mHtmlFragment.canGoBack()) {
                return true;
            } else if (mIsRoot) {
                exitByDoubleClick(); //调用双击退出函数
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(final View v) {
    }


    private void exitByDoubleClick() {
        Timer tExit;
        if (!isExit) {
            isExit = true;
            // 准备退出
            showToast(getString(R.string.click_back_two_times));
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
}
