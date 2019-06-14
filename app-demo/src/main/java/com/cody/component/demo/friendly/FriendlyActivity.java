/*
 * ************************************************************
 * 文件：FriendlyActivity.java  模块：app-demo  项目：component
 * 当前修改时间：2019年06月13日 17:34:56
 * 上次修改时间：2019年06月13日 17:34:56
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-demo
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo.friendly;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.cody.component.app.widget.friendly.FriendlyLayout;
import com.cody.component.app.widget.friendly.IFriendlyView;
import com.cody.component.demo.R;


public class FriendlyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly);
       FriendlyLayout friendlyLayout = findViewById(R.id.friendlyView);
       friendlyLayout.setIFriendlyView(new IFriendlyView() {
           @Override
           public int initView() {
               return 0;
           }
       });
    }
}
