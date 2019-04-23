/*
 * ************************************************************
 * 文件：PageInfo.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.define;

import android.text.TextUtils;

import com.cody.component.lib.bean.ListBean;
import com.cody.component.lib.bean.Result;

/**
 * Created by xu.yi. on 2019/4/8.
 * 保留页面信息，做分页加载需要用
 */
public class PageInfo {
    public final static int DEFAULT_PAGE_NO = 0;
    public final static int DEFAULT_PAGE_SIZE = 20;
    public final static int DEFAULT_POSITION = -1;
    public final static int DEFAULT_PREFETCH_DISTANCE = 5;
    private int mPageNo;
    private int mPageSize;
    private int mPosition;

    public PageInfo(int pageNo, int pageSize, int position) {
        mPageNo = pageNo;
        mPageSize = pageSize;
        mPosition = position;
    }

    public static <ItemBean> PageInfo getPrePageInfo(Result<ListBean<ItemBean>> result) {
        if (result == null || result.getData() == null) {
            return null;
        }
        return getPageInfo(result.getData().getPrePosition());
    }

    public static <ItemBean> PageInfo getNextPageInfo(Result<ListBean<ItemBean>> result) {
        if (result == null || result.getData() == null) {
            return null;
        }
        return getPageInfo(result.getData().getNextPosition());
    }

    public static PageInfo getNextPageInfo(PageInfo lastPageInfo) {
        if (lastPageInfo == null) {
            return defaultPageInfo();
        }
        lastPageInfo.mPageNo++;
        return lastPageInfo;
    }

    public static PageInfo getPageInfo(String position) {
        if (position == null || TextUtils.isDigitsOnly(position)) {
            return null;
        }
        return new PageInfo(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE, Integer.valueOf(position));
    }

    public static PageInfo defaultPageInfo() {
        return new PageInfo(DEFAULT_PAGE_NO, DEFAULT_PAGE_SIZE, DEFAULT_POSITION);
    }

    public int getPageNo() {
        return mPageNo;
    }

    public void setPageNo(int pageNo) {
        mPageNo = pageNo;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public int getPageSize() {
        return mPageSize;
    }

    public void setPageSize(int pageSize) {
        mPageSize = pageSize;
    }
}