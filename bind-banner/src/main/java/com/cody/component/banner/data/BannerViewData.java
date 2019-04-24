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
import com.cody.component.handler.data.ViewData;

/**
 * Created by cody.yi on 2019/4/4.
 * banner viewData
 */
public class BannerViewData extends ViewData {
    private static final long serialVersionUID = 4802021333332679648L;
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
    public boolean areItemsTheSame(IViewData newBind) {
        if (newBind instanceof BannerViewData) {
            return TextUtils.equals(this.mImgId, (((BannerViewData) newBind).mImgId));
        }
        return super.areItemsTheSame(newBind);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BannerViewData that = (BannerViewData) o;

        if (mImgId != null ? !mImgId.equals(that.mImgId) : that.mImgId != null) return false;
        if (mImgDesc != null ? !mImgDesc.equals(that.mImgDesc) : that.mImgDesc != null) return false;
        if (mImgUrl != null ? !mImgUrl.equals(that.mImgUrl) : that.mImgUrl != null) return false;
        if (mImgSize != null ? !mImgSize.equals(that.mImgSize) : that.mImgSize != null) return false;
        return mLinkUrl != null ? mLinkUrl.equals(that.mLinkUrl) : that.mLinkUrl == null;
    }

    @Override
    public int hashCode() {
        int result = mImgId != null ? mImgId.hashCode() : 0;
        result = 31 * result + (mImgDesc != null ? mImgDesc.hashCode() : 0);
        result = 31 * result + (mImgUrl != null ? mImgUrl.hashCode() : 0);
        result = 31 * result + (mImgSize != null ? mImgSize.hashCode() : 0);
        result = 31 * result + (mLinkUrl != null ? mLinkUrl.hashCode() : 0);
        return result;
    }
}
