/*
 * ************************************************************
 * 文件：IHeaderListener.java  模块：http-core  项目：component
 * 当前修改时间：2019年04月05日 23:43:33
 * 上次修改时间：2019年04月05日 23:11:29
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.http.core.http.listener;

/**
 * @author codyer .
 * @description Callback interface for delivering header responses.
 * @date 16/9/10.
 */

import java.util.Map;

/**
 * Callback interface for delivering header responses.
 */
public interface IHeaderListener {
    /**
     * Called when a response header is received.
     */
    void onHeaderResponse(Map<String, String> header);
}
