/*
 * ************************************************************
 * 文件：BusDemoActivity.java  模块：app  项目：component
 * 当前修改时间：2019年04月23日 18:23:19
 * 上次修改时间：2019年04月13日 08:43:55
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo.bus;

import android.os.Bundle;

import com.cody.component.bus.LiveEventBus;
import com.cody.component.demo.R;
import com.cody.component.demo.bean.TestBean;
import com.cody.component.demo.bus.event.Scope$demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cody.component.bus.wrapper.ObserverWrapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class BusDemoActivity extends AppCompatActivity {
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                count++;
                Snackbar.make(view, "发送事件监听" + count, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                LiveEventBus.begin().inScope(Scope$demo.class).testBean().setValue(new TestBean("count", ("count" + count)));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                Toast.makeText(BusDemoActivity.this, "注册事件监听", Toast.LENGTH_SHORT).show();
                LiveEventBus.begin().inScope(Scope$demo.class).testBean()
                        .observeAny(BusDemoActivity.this, new ObserverWrapper<TestBean>() {
                            @Override
                            public void onChanged(TestBean testBean) {
                                Toast.makeText(BusDemoActivity.this, "事件监听" + testBean.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
