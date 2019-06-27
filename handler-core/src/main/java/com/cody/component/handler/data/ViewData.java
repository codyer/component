/*
 * ************************************************************
 * 文件：ViewData.java  模块：handler-core  项目：component
 * 当前修改时间：2019年04月24日 09:39:11
 * 上次修改时间：2019年04月23日 18:23:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：handler-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.handler.data;

/**
 * Created by xu.yi. on 2019/3/26.
 * 和界面绑定的数据基类默认实现
 * 使用了LiveData类型变量需要实现Cloneable接口，不然绑定会失效，基类实现就OK了？待确认
 * 所有非LiveData变量不要定义final，需要每次mapper的时候创建，不然equals直接不会走，底层就当数据没有变化了
 * 
 * 如果非LiveData包含LiveData子变量，类似List<ItemData> List要使用浅拷贝，或者直接使用Mapper，
 * 如果不传OldList，那么每个mapper不可以共用，每个List要有自己的Mapper，因为Mapper会持有OldList的引用。
 * 如果传入OldList那就可以共用。
 */
public class ViewData implements IViewData, Cloneable {

    @Override
    public boolean areItemsTheSame(IViewData newData) {
        return this == newData;
    }

    @Override
    public boolean areContentsTheSame(IViewData newData) {
        return this.equals(newData);
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
