/*
 * ************************************************************
 * 文件：MonitorPayloadFragment.java  模块：http-monitor  项目：component
 * 当前修改时间：2019年04月05日 18:45:24
 * 上次修改时间：2019年04月05日 17:27:09
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.monitor.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.cody.component.base.app.fragment.EmptyBindFragment;
import com.cody.http.monitor.R;
import com.cody.http.monitor.databinding.MonitorFragmentPayloadBinding;
import com.cody.http.monitor.db.data.ItemMonitorData;
import com.cody.http.monitor.viewmodel.MonitorViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

/**
 * Created by xu.yi. on 2019/4/5.
 * MonitorPayloadFragment
 */
public class MonitorPayloadFragment extends EmptyBindFragment<MonitorFragmentPayloadBinding> {
    private static final int TYPE_REQUEST = 100;
    private static final int TYPE_RESPONSE = 200;
    private static final String TYPE_KEY = "keyType";
    private int type;

    private static MonitorPayloadFragment newInstance(int type) {
        MonitorPayloadFragment fragment = new MonitorPayloadFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MonitorPayloadFragment newInstanceRequest() {
        return newInstance(TYPE_REQUEST);
    }

    public static MonitorPayloadFragment newInstanceResponse() {
        return newInstance(TYPE_RESPONSE);
    }

    public MonitorPayloadFragment() {
    }

    @Override
    protected int getLayoutID() {
        return R.layout.monitor_fragment_payload;
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
        getViewModel(MonitorViewModel.class).getRecordLiveData().observe(this, new Observer<ItemMonitorData>() {
            @Override
            public void onChanged(@Nullable ItemMonitorData monitorItemMonitorData) {
                if (monitorItemMonitorData != null) {
                    switch (type) {
                        case TYPE_REQUEST: {
                            setText(monitorItemMonitorData.getRequestHeadersString(true),
                                    monitorItemMonitorData.getFormattedRequestBody(), monitorItemMonitorData.isRequestBodyIsPlainText());
                            break;
                        }
                        case TYPE_RESPONSE: {
                            setText(monitorItemMonitorData.getResponseHeadersString(true),
                                    monitorItemMonitorData.getFormattedResponseBody(), monitorItemMonitorData.isResponseBodyIsPlainText());
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