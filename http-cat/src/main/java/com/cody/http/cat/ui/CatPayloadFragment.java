/*
 * ************************************************************
 * 文件：CatPayloadFragment.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.cat.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.cody.component.app.fragment.EmptyBindFragment;
import com.cody.http.cat.R;
import com.cody.http.cat.databinding.CatFragmentPayloadBinding;
import com.cody.http.cat.db.data.ItemHttpData;
import com.cody.http.cat.viewmodel.CatViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatPayloadFragment
 */
public class CatPayloadFragment extends EmptyBindFragment<CatFragmentPayloadBinding> {
    private static final int TYPE_REQUEST = 100;
    private static final int TYPE_RESPONSE = 200;
    private static final String TYPE_KEY = "keyType";
    private int type;

    private static CatPayloadFragment newInstance(int type) {
        CatPayloadFragment fragment = new CatPayloadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CatPayloadFragment newInstanceRequest() {
        return newInstance(TYPE_REQUEST);
    }

    public static CatPayloadFragment newInstanceResponse() {
        return newInstance(TYPE_RESPONSE);
    }

    public CatPayloadFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.cat_fragment_payload;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt(TYPE_KEY);
        }
        getViewModel(CatViewModel.class).getRecordLiveData().observe(this, new Observer<ItemHttpData>() {
            @Override
            public void onChanged(@Nullable ItemHttpData catItemHttpData) {
                if (catItemHttpData != null) {
                    switch (type) {
                        case TYPE_REQUEST: {
                            setText(catItemHttpData.getRequestHeadersString(true),
                                    catItemHttpData.getFormattedRequestBody(), catItemHttpData.isRequestBodyIsPlainText());
                            break;
                        }
                        case TYPE_RESPONSE: {
                            setText(catItemHttpData.getResponseHeadersString(true),
                                    catItemHttpData.getFormattedResponseBody(), catItemHttpData.isResponseBodyIsPlainText());
                            break;
                        }
                    }
                }
            }
        });
    }

    private void setText(String headersString, String bodyString, boolean isPlainText) {
        if (TextUtils.isEmpty(headersString)) {
            getBinding().headers.setVisibility(View.GONE);
        } else {
            getBinding().headers.setVisibility(View.VISIBLE);
            getBinding().headers.setText(Html.fromHtml(headersString));
        }
        if (!isPlainText) {
            getBinding().body.setText("(encoded or binary body omitted)");
        } else {
            getBinding().body.setText(bodyString);
        }
    }
}