/*
 * ************************************************************
 * 文件：RequestStatusUtil.java  模块：handler-core  项目：component
 * 当前修改时间：2020年04月20日 11:03:51
 * 上次修改时间：2019年07月18日 14:47:51
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2020
 * ************************************************************
 */

package com.cody.component.handler;

import com.cody.component.handler.define.RequestStatus;
import com.cody.component.lib.bean.ListBean;

import java.util.List;

/**
 * Created by xu.yi. on 2019/3/29.
 * 请求状态工具类
 */
public class RequestStatusUtil {
    /**
     * 判断请求结果
     */
    public static RequestStatus getRequestStatus(RequestStatus requestStatus) {
        if (requestStatus.isLoadingAfter() || requestStatus.isLoadingBefore()) {
            return requestStatus.end();
        }
        return requestStatus.empty();
    }

    /**
     * 判断请求结果
     */
    public static RequestStatus getRequestStatus(RequestStatus requestStatus, List data) {
        if (data == null || data.isEmpty()) {
            return getRequestStatus(requestStatus);
        }
        return requestStatus.loaded();
    }

    /**
     * 判断请求结果
     */
    public static RequestStatus getRequestStatus(RequestStatus requestStatus, ListBean data) {
        if (data == null || data.getItems() == null || data.getItems().isEmpty()) {
            return getRequestStatus(requestStatus);
        }

        //没有更多了
        if (!data.isMore()) {
            return requestStatus.end();
        }
        return requestStatus.loaded();
    }
}
