/*
 * ************************************************************
 * 文件：OnBindingItemClickListener.java  模块：bind-adapter  项目：component
 * 当前修改时间：2019年04月04日 14:43:21
 * 上次修改时间：2019年04月04日 14:32:15
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.adapter.list;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Interface definition for a callback to be invoked when an item in this
 * AdapterView has been clicked.
 */
public interface OnBindingItemClickListener {

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    void onItemClick(RecyclerView parent, View view, int position, long id);
}