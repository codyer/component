/*
 * ************************************************************
 * 文件：MaskViewData.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月11日 15:06:51
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.data;

import com.cody.component.lib.safe.SafeMutableLiveData;
import com.cody.component.list.R;
import com.cody.component.lib.data.ViewData;

/**
 * Created by xu.yi. on 2019/4/9.
 * 页面出错、无网络、无数据等布局需要的数据
 */
public class MaskViewData extends ViewData {
    private static final long serialVersionUID = -381194114860141421L;
    private final SafeMutableLiveData<Boolean> mVisibility = new SafeMutableLiveData<>(false);
    private final SafeMutableLiveData<Integer> mInfoId = new SafeMutableLiveData<>(R.string.ui_str_no_content);
    private final SafeMutableLiveData<String> mMessage = new SafeMutableLiveData<>("");
    private final SafeMutableLiveData<Integer> mImageId = new SafeMutableLiveData<>(R.drawable.ic_no_content);

    public void hideMaskView() {
        mVisibility.postValue(false);
    }

    public void noContentView() {
        mVisibility.postValue(true);
        mInfoId.postValue(R.string.ui_str_no_content);
        mImageId.postValue(R.drawable.ic_no_content);
    }

    public void badNetWorkView() {
        mVisibility.postValue(true);
        mInfoId.postValue(R.string.ui_str_bad_network_view_tip);
        mImageId.postValue(R.drawable.ic_bad_network);
    }

    public void failedView(String message) {
        mMessage.postValue(message);
        failedView();
    }

    public void failedView() {
        mVisibility.postValue(true);
        mInfoId.postValue(R.string.ui_str_load_failed_click_to_reload);
        mImageId.postValue(R.drawable.ic_load_failed);
    }

    public MaskViewData() {
    }

    public SafeMutableLiveData<Boolean> getVisibility() {
        return mVisibility;
    }

    public SafeMutableLiveData<Integer> getInfoId() {
        return mInfoId;
    }

    public SafeMutableLiveData<String> getMessage() {
        return mMessage;
    }

    public SafeMutableLiveData<Integer> getImageId() {
        return mImageId;
    }
}
