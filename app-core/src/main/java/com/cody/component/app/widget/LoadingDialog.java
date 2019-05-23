/*
 * ************************************************************
 * 文件：LoadingDialog.java  模块：app-core  项目：component
 * 当前修改时间：2019年05月23日 14:36:06
 * 上次修改时间：2019年05月23日 14:36:05
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cody.component.app.R;
import com.cody.component.util.NotProguard;

/**
 * Created by xu.yi. on 2019-05-23.
 * component
 */
@NotProguard
public class LoadingDialog extends Dialog {
    /**
     * 提示文字
     */
    private TextView mTextView;

    public LoadingDialog(Context context) {
        //使用自定义的Style
        super(context, R.style.WinDialog);
        //使用自定义的layout
        setContentView(R.layout.loading_dialog);
        if (getWindow() != null) {
            getWindow().setDimAmount(0.1f);
        }
        mTextView = findViewById(android.R.id.message);
    }

    public void setText(String s) {
        if (mTextView != null) {
            mTextView.setText(s);
            mTextView.setVisibility(View.VISIBLE);
        }
    }

    public void setText(int res) {
        if (mTextView != null) {
            mTextView.setText(res);
            mTextView.setVisibility(View.VISIBLE);
        }
    }
}
