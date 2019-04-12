/*
 * ************************************************************
 * 文件：CatPayloadFragment.java  模块：http-cat  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月07日 18:02:21
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：http-cat
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

/**
 * Created by xu.yi. on 2019/4/5.
 * CatPayloadFragment
 */
public class CatPayloadFragment extends EmptyBindFragment<CatFragmentPayloadBinding> {
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
        bundle.putSerializable(ITEM_VIEW_DATA, itemHttpData);
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
            mItemHttpData = (ItemHttpData) bundle.getSerializable(ITEM_VIEW_DATA);
        }
        if (mItemHttpData != null) {
            switch (mType) {
                case TYPE_REQUEST: {
                    setText(mItemHttpData.getRequestHeadersString(true),
                            mItemHttpData.getFormattedRequestBody(), mItemHttpData.isRequestBodyIsPlainText());
                    break;
                }
                case TYPE_RESPONSE: {
                    setText(mItemHttpData.getResponseHeadersString(true),
                            mItemHttpData.getFormattedResponseBody(), mItemHttpData.isResponseBodyIsPlainText());
                    break;
                }
            }
        }
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