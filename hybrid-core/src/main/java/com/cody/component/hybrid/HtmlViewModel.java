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

import com.cody.component.handler.define.Operation;
import com.cody.component.handler.define.ViewAction;
import com.cody.component.handler.interfaces.DataCallBack;
import com.cody.component.handler.viewmodel.SingleViewModel;
import com.cody.component.hybrid.data.HtmlViewData;


/**
 * Created by xu.yi. on 2019-04-30.
 * component html
 */
public class HtmlViewModel extends SingleViewModel<HtmlViewData> {

    public HtmlViewModel(final HtmlViewData friendlyViewData) {
        super(friendlyViewData);
    }

    @Override
    public void OnRequestData(final Operation operation, final DataCallBack callBack) {
        setAction(ViewAction.DEFAULT);
        callBack.onComplete();
    }
}
