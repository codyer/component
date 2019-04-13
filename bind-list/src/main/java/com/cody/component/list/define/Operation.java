/*
 * ************************************************************
 * 文件：Operation.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 15:52:45
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：bind-list
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.list.define;

/**
 * Created by xu.yi. on 2019/4/8.
 * 对列表进行操作的状态
 */
public enum Operation {
    INIT,
    REFRESH,
    LOAD_BEFORE,
    LOAD_AFTER
}
