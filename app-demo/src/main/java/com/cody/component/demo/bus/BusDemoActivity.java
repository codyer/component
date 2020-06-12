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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

//import com.cody.component.bus.LiveEventBus;
import com.cody.component.bus.LiveEventBus;
import com.cody.component.bus.wrapper.ObserverWrapper;
import com.cody.component.demo.R;
import com.cody.component.demo.bean.TestBean;
import com.cody.component.demo.bus.event.Scope$demo;
import com.cody.component.util.LogUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.greenrobot.eventbus.ThreadMode;

public class BusDemoActivity extends AppCompatActivity {
    private static int count = 0;
    private TestThread testThread;

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
//            EventBus.getDefault().post("hello" + count);
//            LiveEventBus.getDefault("login", String.class).post("LiveEventBus");
            LiveEventBus.getDefault("login", TestBean.class).post(new TestBean("count", ("count" + count)));
            Snackbar.make(view, "发送事件监听" + count, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            LiveEventBus.begin().inScope(Scope$demo.class).testBean().post(new TestBean("count", ("count" + count)));
        });

        LogUtil.d("ddd", "1 id=" + Thread.currentThread().getId());
        // 只支持在主线程调用观察者
        // 线程执行某个耗时任务，同时监听事件回调
        testThread = new TestThread(() -> {
            LogUtil.d("ddd", "TestThread id=" + Thread.currentThread().getId());
            try {
                int i = 0;
                while (true) {
                    if (i++ < 10) {
                        Thread.sleep(1000);
                        LogUtil.d("ddd", "TestThread 处理时间用了：" + i + "s id=" + Thread.currentThread().getId());
                        if (i > 3 && i < 6) {
//                            EventBus.getDefault().post("TestThread" + i);
                        }
                    } else {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        testThread.start();
        /*LiveEventBus.getDefault("login", Object.class).observe(this, new ObserverWrapper<Object>() {
            @Override
            public void onChanged(@Nullable final Object hello) {
                Toast.makeText(BusDemoActivity.this, "事件监听 in Object=" + hello, Toast.LENGTH_SHORT).show();
            }
        });
        LiveEventBus.getDefault("login", TestBean.class).observe(this, new ObserverWrapper<TestBean>() {
            @Override
            public void onChanged(@Nullable final TestBean hello) {
                Toast.makeText(BusDemoActivity.this, "事件监听 in TestBean=" + hello, Toast.LENGTH_SHORT).show();
            }
        });*/
//        EventBus.getDefault().register(testThread);
//        EventBus.getDefault().register(this);
    }

    final class TestThread extends Thread {
        public TestThread(final Runnable target) {
            super(target);
        }

//        @Subscribe(threadMode = ThreadMode.POSTING)
        public void test(String hello) {
            LogUtil.d("ddd", "TestThread in test id=" + Thread.currentThread().getId());
            Toast.makeText(BusDemoActivity.this, "事件监听 in TestThread" + hello, Toast.LENGTH_SHORT).show();
        }
    }

//    @Subscribe
    public void test(String hello) {
        Toast.makeText(BusDemoActivity.this, "事件监听 in test" + hello, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
//        EventBus.getDefault().unregister(this);
//        EventBus.getDefault().unregister(testThread);
        super.onStop();
    }
/*

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
                        */
/*if (finish) {
                            LiveEventBus.begin()
                                    .inScope(Scope$demo.class)
                                    .testBean().removeObserver(this);
                        }*//*

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
*/

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
//                Toast.makeText(BusDemoActivity.this, "注册事件监听", Toast.LENGTH_SHORT).show();
               /* LiveEventBus.getDefault("Event1",String.class).observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(final String event) {
                        Toast.makeText(BusDemoActivity.this, "后注册的监听到数据 -> " + event, Toast.LENGTH_SHORT).show();
                    }
                });*/
                LiveEventBus.begin().inScope(Scope$demo.class).testBean()
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
