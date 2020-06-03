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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cody.component.bus.LiveEventBus;
import com.cody.component.bus.wrapper.ObserverWrapper;
import com.cody.component.demo.R;
import com.cody.component.demo.bean.TestBean;
import com.cody.component.demo.bus.event.Scope$demo;
import com.cody.component.util.LogUtil;
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
        fab.setOnClickListener(view -> {
            count++;
            Snackbar.make(view, "发送事件监听" + count, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            LiveEventBus.begin().inScope(Scope$demo.class).testBean().post(new TestBean("count", ("count" + count)));
        });

        LogUtil.d("ddd", "1 id=" + Thread.currentThread().getId());
        // 只支持在主线程调用观察者
        // 线程执行某个耗时任务，同时监听事件回调
        new Thread(() -> {
            LogUtil.d("ddd", "0 id=" + Thread.currentThread().getId());
            testThread1();
        }).start();
    }

    private void testThread1() {
        LiveEventBus.begin()
                .inScope(Scope$demo.class)
                .testBean()
                .observe(this, new ObserverWrapper<TestBean>(false, false) {
                    @Override
                    public void onChanged(final TestBean testBean) {
                        boolean finish = TextUtils.equals(testBean.getCode(), "count5");
                        LogUtil.d("ddd", "2 事件监听 in thread id=" + Thread.currentThread().getId());
                        //Toast.makeText(BusDemoActivity.this, "事件监听 in thread" + testBean.toString(), Toast.LENGTH_SHORT).show();
                        /*if (finish) {
                            LiveEventBus.begin()
                                    .inScope(Scope$demo.class)
                                    .testBean().removeObserver(this);
                        }*/
                    }
                });
        try {
            int i = 0;
            while (true) {
                if (i++ < 10) {
                    Thread.sleep(1000);
                    LiveEventBus.begin().inScope(Scope$demo.class).testBean().post(new TestBean("count", ("count" + count)));
                    LogUtil.d("ddd", "处理时间用了：" + i + "s id=" + Thread.currentThread().getId());
                } else {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
                        .observe(BusDemoActivity.this, new ObserverWrapper<TestBean>() {
                            @Override
                            protected boolean isSticky() {
                                return true;
                            }

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
