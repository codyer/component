/*
 * ************************************************************
 * 文件：HtmlFragment.java  模块：hybrid-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月14日 00:14:46
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：hybrid-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.hybrid.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;

import androidx.annotation.NonNull;

import com.cody.component.app.local.BaseLocalKey;
import com.cody.component.app.local.Repository;
import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.handler.define.ViewAction;
import com.cody.component.handler.interfaces.Scrollable;
import com.cody.component.hybrid.HtmlViewModel;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.hybrid.OnUrlListener;
import com.cody.component.hybrid.R;
import com.cody.component.hybrid.core.JsWebChromeClient;
import com.cody.component.hybrid.data.HtmlViewData;
import com.cody.component.hybrid.databinding.FragmentHtmlBinding;
import com.cody.component.hybrid.handler.JsHandlerCommonImpl;
import com.cody.component.image.ImageViewDelegate;
import com.cody.component.image.OnImageViewListener;
import com.cody.component.util.LogUtil;
import com.lzy.imagepicker.bean.ImageItem;

import java.io.File;
import java.util.List;

/**
 * Html 页面具体实现
 */
public class HtmlFragment extends com.cody.component.app.fragment.FriendlyBindFragment<FragmentHtmlBinding, HtmlViewModel>
        implements OnImageViewListener, JsWebChromeClient.OpenFileChooserCallBack, Scrollable {
    private static final String HTML_URL = "html_url";
    private ImageViewDelegate mImageViewDelegate;
    private ValueCallback<Uri[]> mFilePathCallback;
    private ValueCallback<Uri> mUploadMsg;

    public HtmlFragment() {
        // Required empty public constructor
    }

    public static HtmlFragment getInstance(String url) {
        HtmlFragment fragment = new HtmlFragment();
        Bundle bundle = new Bundle();
        bundle.putString(HTML_URL, url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutID() {
        return R.layout.fragment_html;
    }

    @Override
    public FriendlyLayout getFriendlyLayout() {
        return getBinding().friendlyView;
    }

    @Override
    public int initView() {
        return R.layout.hybrid_friendly_init_view;
    }

    @Override
    public int errorView() {
        return R.layout.hybrid_friendly_error_view;
    }

    @Override
    protected void onFirstUserVisible(final Bundle savedInstanceState) {
        super.onFirstUserVisible(savedInstanceState);
        // syncCookie
        JsBridge.getInstance().syncCookie(getActivity(), getViewData().getUrlHost(), Repository.getLocalMap(BaseLocalKey.COOKIE));
    }

    @Override
    protected void onUserVisible() {
        super.onUserVisible();
        // syncCookie
        JsBridge.getInstance().syncCookie(getActivity(), getViewData().getUrlHost(), Repository.getLocalMap(BaseLocalKey.COOKIE));
    }

    @Override
    public boolean childHandleScrollVertically(final View target, final int direction) {
        return !JsBridge.getInstance().isRefreshable() || getBinding().webView.getScrollY() > 0 || getBinding().webView.canScrollVertically(direction);
    }

    @Override
    public HtmlViewModel buildFriendlyViewModel() {
        HtmlViewData htmlViewData = new HtmlViewData();
        Bundle bundle = getArguments();
        if (bundle != null) {
            String url = bundle.getString(HTML_URL);
            if (url != null) {
                htmlViewData.setUrl(url);
            } else {
                finish();
            }
        }
        return new HtmlViewModel(htmlViewData);
    }

    @Override
    public HtmlViewData getViewData() {
        return getFriendlyViewModel().getFriendlyViewData();
    }

    @NonNull
    @Override
    public Class<HtmlViewModel> getVMClass() {
        return HtmlViewModel.class;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (unBound()) return;
        mImageViewDelegate = new ImageViewDelegate(this);
        JsBridge.getInstance()
                .addJsHandler(JsHandlerCommonImpl.class.getSimpleName(), JsHandlerCommonImpl.class)
                .setFileChooseCallBack(this)
                .build(getBinding().webView, getFriendlyViewModel());

        if (null != savedInstanceState) {
            getBinding().webView.restoreState(savedInstanceState);
        }

        if (getActivity() instanceof OnUrlListener) {
            getViewData().getHeader().observe(this, title -> ((OnUrlListener) getActivity()).onTitleChange(title));
            getViewData().getUrl().observe(this, url -> ((OnUrlListener) getActivity()).onUrlChange(url, canGoBack()));
        }
    }

    @Override
    protected void onExecuteAction(final ViewAction action) {
        super.onExecuteAction(action);
        if (action == null) return;
        switch (action.getAction()) {
            case ViewAction.DEFAULT:
                getViewData().setProgress(0);
                if (getFriendlyViewModel().getRequestStatus().isInitializing() ||
                        getFriendlyViewModel().getRequestStatus().isRefreshing() ||
                        getFriendlyViewModel().getRequestStatus().isRetrying()) {
                    getBinding().webView.loadUrl(getViewData().getUrl().get());
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        JsBridge.onResume(getBinding().webView);
    }

    @Override
    public void onPause() {
        super.onPause();
        JsBridge.onPause(getBinding().webView);
    }

    @Override
    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {
        LogUtil.d("OpenFileChooserCallBack acceptType=" + acceptType);
        mUploadMsg = uploadMsg;
        if (mImageViewDelegate != null) {
            mImageViewDelegate.pickImage(1, false);
        }
    }

    @Override
    public void showFileChooserCallBack(final ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        LogUtil.d("OpenFileChooserCallBack fileChooserParams=" + fileChooserParams);
        mFilePathCallback = filePathCallback;
        if (mImageViewDelegate != null) {
            mImageViewDelegate.pickImage(1, false);
        }
    }

    @Override
    public void onPreview(int id, List<ImageItem> images) {
    }

    @Override
    public void onPickImage(int id, List<ImageItem> images) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (mUploadMsg == null || images == null || images.size() < 1) {
                return;
            }
            String url = images.get(0).path;
            if (TextUtils.isEmpty(url) || !new File(url).exists()) {
                return;
            }
            Uri uri = Uri.fromFile(new File(url));
            mUploadMsg.onReceiveValue(uri);
            mUploadMsg = null;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // for android 5.0+
            if (mFilePathCallback == null || images == null || images.size() < 1) {
                return;
            }

            Uri[] uris = new Uri[images.size()];
            for (int i = 0; i < images.size(); i++) {
                if (TextUtils.isEmpty(images.get(i).path) || !new File(images.get(i).path).exists()) {
                    continue;
                }
                uris[i] = Uri.fromFile(new File(images.get(i).path));
            }
            mFilePathCallback.onReceiveValue(uris);
            mFilePathCallback = null;
        }
    }

    @Override
    public void onDestroy() {
        JsBridge.onDestroy(getBinding().webView);
        releaseFileChoose();
        mImageViewDelegate = null;
        super.onDestroy();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        releaseFileChoose();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JsBridge.onActivityResult(requestCode, resultCode, data);
        if (mImageViewDelegate != null) {
            mImageViewDelegate.onActivityResult(requestCode, resultCode, data);
        }
        releaseFileChoose();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getBinding().webView.saveState(outState);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.ignore) {
            getViewData().setIgnoreError(true);
            getFriendlyLayout().removeFriendLyView();
        }
    }

    private void releaseFileChoose() {
        if (mUploadMsg != null) {
            mUploadMsg.onReceiveValue(null);
            mUploadMsg = null;
        }

        if (mFilePathCallback != null) {         // for android 5.0+
            mFilePathCallback.onReceiveValue(null);
            mFilePathCallback = null;
        }
    }

    public boolean canGoBack() {
        return getBinding().webView.canGoBack();
    }

    public boolean goBack() {
        if (getBinding().webView.canGoBack()) {
            getBinding().webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void scrollToTop() {
        getBinding().webView.scrollTo(0, 0);
    }
}
