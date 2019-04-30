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

import com.cody.component.app.fragment.SimpleBindFragment;
import com.cody.component.app.fragment.SingleBindFragment;
import com.cody.component.handler.define.ViewAction;
import com.cody.component.handler.interfaces.Refreshable;
import com.cody.component.handler.interfaces.Scrollable;
import com.cody.component.hybrid.HtmlViewModel;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.hybrid.OnUrlListener;
import com.cody.component.hybrid.R;
import com.cody.component.hybrid.activity.HtmlActivity;
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

import androidx.annotation.NonNull;

/**
 * Html 页面具体实现
 */
public class HtmlFragment extends SingleBindFragment<FragmentHtmlBinding, HtmlViewModel, HtmlViewData>
        implements OnImageViewListener, JsWebChromeClient.OpenFileChooserCallBack, Scrollable, Refreshable {
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
        return super.getViewData();
    }

    @NonNull
    @Override
    public Class<HtmlViewModel> getVMClass() {
        return HtmlViewModel.class;
    }

    @Override
    protected void onBaseReady(final Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        if (!isBound()) return;
        mImageViewDelegate = new ImageViewDelegate(this);
        JsBridge.getInstance()
                .addJsHandler(JsHandlerCommonImpl.class.getSimpleName(), JsHandlerCommonImpl.class)
                // TODO syncCookie
//                .syncCookie(this, mHtmlViewData.getUrl().getValue(), Repository.getLocalMap(BaseLocalKey.X_TOKEN))
                .setFileChooseCallBack(this)
                .build(getBinding().webView, getFriendlyViewModel());

        if (null != savedInstanceState) {
            getBinding().webView.restoreState(savedInstanceState);
        }

        if (getActivity() instanceof OnUrlListener) {
            getViewData().getUrl().observe(this, url -> ((OnUrlListener) getActivity()).onUrlChange(canGoBack()));
        }
    }

    @Override
    protected void onExecuteAction(final ViewAction action) {
        super.onExecuteAction(action);
        if (action == null) return;
        switch (action.getAction()) {
            case ViewAction.DEFAULT:
                getViewData().setProgress(0);
                if (getFriendlyViewModel().getRequestStatus().isInitializing()) {
                    getBinding().webView.loadUrl(getViewData().getUrl().getValue());
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        JsBridge.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        JsBridge.onPause();
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
        JsBridge.onDestroy();
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
            getViewData().hideMaskView();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        JsBridge.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
