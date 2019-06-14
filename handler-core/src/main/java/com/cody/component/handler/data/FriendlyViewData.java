/*
 * ************************************************************
 * 文件：FriendlyViewData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年06月13日 15:09:08
 * 上次修改时间：2019年05月27日 17:13:03
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.cody.component.handler.R;
import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.RequestStatus;
import com.cody.component.handler.livedata.BooleanLiveData;
import com.cody.component.handler.livedata.IntegerLiveData;
import com.cody.component.handler.livedata.StringLiveData;

/**
 * Created by xu.yi. on 2019/4/9.
 * 页面出错、无网络、无数据等布局需要的数据
 */
public class FriendlyViewData extends ViewData {
    private RequestStatus mRequestStatus = new RequestStatus();
    final private BooleanLiveData mVisibility = new BooleanLiveData(true);
    private BooleanLiveData mInitialized = new BooleanLiveData(false);//已经初始化
    /**
     * 提交请求状态
     */
    @UiThread
    public void submitStatus(final RequestStatus status) {
        if (status != null) {
            switch (status.getOperation()) {
                case INIT:
                    switch (status.getStatus()) {
                        case RUNNING:
                            mVisibility.setValue(true);
                            break;
                        case SUCCESS:
                            mVisibility.setValue(false);
                            break;
                        case EMPTY:
                            break;
                        case END:
                            break;
                        case CANCEL:
                            break;
                        case FAILED:
                            break;
                    }
                    break;
                case REFRESH:
                    break;
                case RETRY:
                    break;
                case LOAD_BEFORE:
                    break;
                case LOAD_AFTER:
                    break;
            }
        }
    }
}
