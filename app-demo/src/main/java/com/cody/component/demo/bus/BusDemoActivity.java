/*
 * ************************************************************
 * 文件：BusDemoActivity.java  模块：app-demo  项目：component
 * 当前修改时间：2021年02月27日 15:30:04
 * 上次修改时间：2021年02月27日 15:25:19
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-demo
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.demo.bus;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.cody.component.demo.R;
import com.cody.component.demo.bean.TestBean;
import com.cody.component.demo.bus.cody.DemoGroupBus;
import com.cody.component.util.LogUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cody.bus.ObserverWrapper;


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
      /*  fab.setOnClickListener(view -> {
            LiveEventBus.getDefault("Event1",String.class).setValue("推送数据"+count++);
        });
        LiveEventBus.getDefault("Event1",String.class).observe(this, new Observer<String>() {
            @Override
            public void onChanged(final String event) {
                Toast.makeText(BusDemoActivity.this, "监听到数据 -> " + event, Toast.LENGTH_SHORT).show();
            }
        });*/
        fab.setOnClickListener(view -> {
            count++;
            Snackbar.make(view, "发送事件监听" + count, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            DemoGroupBus.testBean().post(new TestBean("count", ("count" + count)));
        });

        LogUtil.d("ddd", "1 id=" + Thread.currentThread().getId());
        // 只支持在主线程调用观察者
    }

    @Override
    protected void onStop() {
//        EventBus.getDefault().unregister(testThread);
        super.onStop();
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
                /*LiveEventBus.getDefault("Event1",String.class).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(final String event) {
                        Toast.makeText(BusDemoActivity.this, "后注册的监听到数据 -> " + event, Toast.LENGTH_SHORT).show();
                    }
                });*/
                DemoGroupBus.testBean()
                        .observe(BusDemoActivity.this, new ObserverWrapper<TestBean>(true) {
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
