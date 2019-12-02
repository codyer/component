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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.cody.component.blues.CrashUtil;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.ViewAction;
import com.cody.component.handler.viewmodel.SingleViewModel;
import com.cody.component.hybrid.activity.HtmlActivity;
import com.cody.component.hybrid.core.UrlUtil;
import com.cody.component.hybrid.data.HtmlViewData;
import com.cody.component.util.ActivityUtil;
import com.cody.component.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;


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
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.contains("tel:") || url.contains("phone:")) {
            url = url.replace("phone:", "").replace("tel:", "");
            ActivityUtil.openDialPage(url);
            return true;
        }
        // 自定义请求在上面处理，拦截其他非http页面
        if (!URLUtil.isValidUrl(url)) {
            Intent intent = null;
            // perform generic parsing of the URI to turn it into an Intent.
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                    if (url.startsWith("android-app://")) {
                        intent = Intent.parseUri(url, Intent.URI_ANDROID_APP_SCHEME);
                    }
                } else {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                }
            } catch (URISyntaxException ex) {
                LogUtil.w("Browser", "Bad URI " + url + ": " + ex.getMessage());
                return false;
            }
            if (intent == null || TextUtils.isEmpty(intent.getScheme())) {
                return false;
            }

            // check whether the intent can be resolved. If not, we will see
            // whether we can download it from the Market.
            // 如果本地没装能响应特殊协议的应用则return
            if (ActivityUtil.getCurrentActivity() == null || intent.resolveActivity(ActivityUtil.getCurrentActivity().getPackageManager()) == null) {
                return false;
            }
            // sanitize the Intent, ensuring web pages can not bypass browser
            // security (only access to BROWSABLE activities).
            intent.addCategory(Intent.CATEGORY_BROWSABLE);

            try {
                if (ActivityUtil.getCurrentActivity().startActivityIfNeeded(intent, -1)) {
                    LogUtil.d("tag", "success use the intent ");
                    return true;
                }
            } catch (ActivityNotFoundException ex) {
                LogUtil.d("tag", "error message is --> " + ex.getMessage());
                // ignore the error. If no application can handle the URL,
                // eg about:blank, assume the browser can handle it.
            } catch (SecurityException se) {
                se.printStackTrace();
            }
            return false;
        }
        //从内部链接跳到外部链接打开新的html页面
        if (UrlUtil.isInnerLink(getFriendlyViewData().getUrl().get())
                && !UrlUtil.isInnerLink(url)) {
            HtmlActivity.startHtml(getFriendlyViewData().getHeader().getValue(), url);
            return true;
        }
        return false;
    }

    public void onPageStarted(final String url) {
        if (!getRequestStatus().isInitializing()) {
            getFriendlyViewData().setUrl(url);
            startOperation(getRequestStatus().loadAfter());
        }
    }

    public void onPageFailure(final String message) {
        submitStatus(getRequestStatus().error(message));
        CrashUtil.postException(new H5Exception(message));
    }

    public void onPageFinished(final String url) {
        getFriendlyViewData().setUrl(url);
        submitStatus(getRequestStatus().loaded());
    }

    public void onProgressChanged(final int progress) {
        getFriendlyViewData().setProgress(progress);
        if (progress == HtmlViewData.MAX_PROGRESS) {
            submitStatus(getRequestStatus().loaded());
        }
    }

    @Override
    public void onRequestData(final Operation operation) {
        setAction(ViewAction.DEFAULT);
    }

}
