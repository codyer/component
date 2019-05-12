/*
 * ************************************************************
 * 文件：BannerViewData.java  模块：bind-banner  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 11:17:58
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-banner
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.banner.data;

import android.text.TextUtils;

import com.cody.component.handler.data.IViewData;
import com.cody.component.handler.data.ItemViewDataHolder;

import java.util.Objects;

/**
 * Created by cody.yi on 2019/4/4.
 * banner viewData
 */
public class BannerViewData extends ItemViewDataHolder {
    private String mImgId;
    private String mImgDesc;
    private String mImgUrl;
    private String mImgSize;
    private String mLinkUrl;

    public BannerViewData() {
    }

    public BannerViewData(String imgUrl) {
        this.mImgUrl = imgUrl;
    }

    @Override
    public boolean areItemsTheSame(IViewData newData) {
        if (newData instanceof BannerViewData) {
            return TextUtils.equals(this.mImgId, (((BannerViewData) newData).mImgId));
        }
        return super.areItemsTheSame(newData);
    }

    public String getImgId() {
        return mImgId;
    }

    public void setImgId(String imgId) {
        mImgId = imgId;
    }

    public String getImgDesc() {
        return mImgDesc;
    }

    public void setImgDesc(String imgDesc) {
        mImgDesc = imgDesc;
    }

    public String getImgUrl() {
        return mImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        mImgUrl = imgUrl;
    }

    public String getImgSize() {
        return mImgSize;
    }

    public void setImgSize(String imgSize) {
        mImgSize = imgSize;
    }

    public String getLinkUrl() {
        return mLinkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        mLinkUrl = linkUrl;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BannerViewData that = (BannerViewData) o;
        return Objects.equals(mImgId, that.mImgId) &&
                Objects.equals(mImgDesc, that.mImgDesc) &&
                Objects.equals(mImgUrl, that.mImgUrl) &&
                Objects.equals(mImgSize, that.mImgSize) &&
                Objects.equals(mLinkUrl, that.mLinkUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mImgId, mImgDesc, mImgUrl, mImgSize, mLinkUrl);
    }
}
