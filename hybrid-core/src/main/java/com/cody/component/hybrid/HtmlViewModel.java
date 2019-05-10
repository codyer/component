/*
 * ************************************************************
 * 文件：HtmlViewModel.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月30日 12:00:48
 * 上次修改时间：2019年04月30日 12:00:48
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid;

import android.text.TextUtils;

import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.ViewAction;
import com.cody.component.handler.interfaces.DataCallBack;
import com.cody.component.handler.viewmodel.SingleViewModel;
import com.cody.component.hybrid.activity.HtmlActivity;
import com.cody.component.hybrid.core.UrlUtil;
import com.cody.component.hybrid.data.HtmlViewData;
import com.cody.component.util.ActivityUtil;


/**
 * Created by xu.yi. on 2019-04-30.
 * component html
 */
public class HtmlViewModel extends SingleViewModel<HtmlViewData> {

    public HtmlViewModel(final HtmlViewData friendlyViewData) {
        super(friendlyViewData);
    }

    public boolean shouldOverrideUrl(String url) {
        if (TextUtils.isEmpty(url)) return true;
        if (url.contains("tel:") || url.contains("phone:")) {
            url = url.replace("phone:", "").replace("tel:", "");
            ActivityUtil.openDialPage(url);
            return true;
        }
        //从内部链接跳到外部链接打开新的html页面
        if (UrlUtil.isInnerLink(getFriendlyViewData().getUrl().getValue())
                && !UrlUtil.isInnerLink(url)) {
            HtmlActivity.startHtml(getFriendlyViewData().getHeader().getValue(), url);
            return true;
        }
        return false;
    }

    public void onPageStarted(final String url) {
        if (!getRequestStatus().isInitializing()) {
            getFriendlyViewData().setUrl(url);
            setOperation(getRequestStatus().loadAfter());
        }
    }

    public void onPageFailure(final String message) {
        onFailure(message);
    }

    public void onPageFinished(final String url) {
        getFriendlyViewData().setUrl(url);
        onComplete(url);
    }

    @Override
    public void OnRequestData(final Operation operation, final DataCallBack callBack) {
        setAction(ViewAction.DEFAULT);
    }
}
