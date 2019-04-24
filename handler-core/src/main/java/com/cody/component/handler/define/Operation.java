/*
 * ************************************************************
 * 文件：Operation.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月23日 18:23:20
 * 上次修改时间：2019年04月23日 18:16:18
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.define;

/**
 * Created by xu.yi. on 2019/4/8.
 * 对进行操作的状态
 */
public enum Operation {
    INIT,
    RETRY,
    REFRESH,
    LOAD_BEFORE,
    LOAD_AFTER
}
