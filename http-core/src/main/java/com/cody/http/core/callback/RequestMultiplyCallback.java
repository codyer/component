/*
 * ************************************************************
 * 文件：RequestMultiplyCallback.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月06日 01:58:30
 * 上次修改时间：2019年03月25日 09:23:00
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.callback;

import com.cody.http.lib.exception.base.BaseException;

/**
 * Created by xu.yi. on 2019/4/6.
 * 自定义处理错误，默认是弹出toast提示错误信息
 */
public interface RequestMultiplyCallback<T> extends RequestCallback<T> {

    void onFail(BaseException e);

}