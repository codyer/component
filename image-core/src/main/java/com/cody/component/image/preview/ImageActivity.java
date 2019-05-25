/*
 * ************************************************************
 * 文件：ImageActivity.java  模块：image-core  项目：component
 * 当前修改时间：2019年05月25日 15:39:42
 * 上次修改时间：2019年05月25日 15:39:42
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：image-core
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.image.preview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cody.component.image.R;

public class ImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
    }
}
