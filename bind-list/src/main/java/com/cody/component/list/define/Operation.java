/*
 * ************************************************************
 * 文件：Operation.java  模块：bind-list  项目：component
 * 当前修改时间：2019年04月12日 09:21:19
 * 上次修改时间：2019年04月09日 15:30:56
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
