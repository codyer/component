/*
 * ************************************************************
 * 文件：HtmlConfig.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月11日 16:40:29
 * 上次修改时间：2019年04月11日 16:40:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.data;

import java.io.Serializable;

/**
 * Created by xu.yi. on 2019/4/11.
 * 打开 html 需要的配置参数保存类
 */
public class HtmlConfig implements Serializable {
    private String mTitle;
    private String mDescription;
    private String mUrl;
    private boolean mIsRoot;
    private boolean mCanShare;

    public String getTitle() {
        return mTitle;
    }

    public HtmlConfig setTitle(final String title) {
        mTitle = title;
        return this;
    }

    public String getDescription() {
        return mDescription;
    }

    public HtmlConfig setDescription(final String description) {
        mDescription = description;
        return this;
    }

    public String getUrl() {
        return mUrl;
    }

    public HtmlConfig setUrl(final String url) {
        mUrl = url;
        return this;
    }

    public boolean isRoot() {
        return mIsRoot;
    }

    public HtmlConfig setRoot(final boolean root) {
        mIsRoot = root;
        return this;
    }

    public boolean isCanShare() {
        return mCanShare;
    }

    public HtmlConfig setCanShare(final boolean canShare) {
        mCanShare = canShare;
        return this;
    }
}
