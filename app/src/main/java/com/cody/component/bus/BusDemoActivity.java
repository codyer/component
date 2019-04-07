/*
 * ************************************************************
 * 文件：BusDemoActivity.java  模块：app  项目：component
 * 当前修改时间：2019年04月04日 18:43:04
 * 上次修改时间：2019年04月03日 21:06:17
 * 作者：Cody.yi   https://github.com/codyer
 *
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.bus;

import android.os.Bundle;

import com.cody.component.R;
import com.cody.component.bean.TestBean;
import com.cody.component.bus.event.Scope$demo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cody.component.bus.core.LiveEventBus;
import com.cody.component.bus.core.wrapper.ObserverWrapper;

public class BusDemoActivity extends AppCompatActivity {
    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                count++;
                Snackbar.make(view, "发送事件监听" + count, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                LiveEventBus.begin().inScope(Scope$demo.class).withEvent$testBean().setValue(new TestBean("count", ("count" + count)));
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
                LiveEventBus.begin().inScope(Scope$demo.class).withEvent$testBean()
                        .observe(BusDemoActivity.this, new ObserverWrapper<TestBean>() {
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
