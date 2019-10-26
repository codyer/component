/*
 * ************************************************************
 * 文件：CatPayloadFragment.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.cat.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.cody.component.app.fragment.StaticFragment;
import com.cody.component.cat.R;
import com.cody.component.cat.databinding.CatFragmentPayloadBinding;
import com.cody.component.cat.db.data.ItemHttpData;

/**
 * Created by xu.yi. on 2019/4/5.
 * CatPayloadFragment
 */
public class CatPayloadFragment extends StaticFragment<CatFragmentPayloadBinding> {
    private static final int TYPE_REQUEST = 100;
    private static final int TYPE_RESPONSE = 200;
    private static final String TYPE_KEY = "keyType";
    private static final String ITEM_VIEW_DATA = "itemHttpData";
    private int mType;
    private ItemHttpData mItemHttpData;

    private static CatPayloadFragment newInstance(int type, ItemHttpData itemHttpData) {
        CatPayloadFragment fragment = new CatPayloadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        bundle.putParcelable(ITEM_VIEW_DATA, itemHttpData);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static CatPayloadFragment newInstanceRequest(ItemHttpData itemHttpData) {
        return newInstance(TYPE_REQUEST, itemHttpData);
    }

    public static CatPayloadFragment newInstanceResponse(ItemHttpData itemHttpData) {
        return newInstance(TYPE_RESPONSE, itemHttpData);
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
        super.onBaseReady(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mType = bundle.getInt(TYPE_KEY);
            mItemHttpData = bundle.getParcelable(ITEM_VIEW_DATA);
        }
        if (mItemHttpData != null) {
            switch (mType) {
                case TYPE_REQUEST: {
                    setText(mItemHttpData.getRequestHeadersString(true),
                            mItemHttpData.getFormattedRequestBody(), mItemHttpData.isRequestBodyIsPlainText(), mItemHttpData.isRequestJson());
                    break;
                }
                case TYPE_RESPONSE: {
                    setText(mItemHttpData.getResponseHeadersString(true),
                            mItemHttpData.getFormattedResponseBody(), mItemHttpData.isResponseBodyIsPlainText(), mItemHttpData.isResponseJson());
                    break;
                }
            }
        }
    }

    private void setText(String headersString, String bodyString, boolean isPlainText, boolean isJson) {
        if (TextUtils.isEmpty(headersString)) {
            getBinding().headers.setVisibility(View.GONE);
        } else {
            getBinding().headers.setVisibility(View.VISIBLE);
            getBinding().headers.setText(Html.fromHtml(headersString));
        }
        if (isJson) {
            getBinding().json.bindJson(bodyString);
        }
        if (!isPlainText) {
            getBinding().body.setText("(encoded or binary body omitted)");
        } else {
            getBinding().body.setText(bodyString);
        }
    }
}