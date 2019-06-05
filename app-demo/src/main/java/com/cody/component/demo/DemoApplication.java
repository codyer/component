/*
 * ************************************************************
 * 文件：DemoApplication.java  模块：app  项目：component
 * 当前修改时间：2019年06月05日 14:15:10
 * 上次修改时间：2019年06月05日 14:02:38
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.demo;

import android.app.Activity;

import com.cody.component.app.BaseApplication;
import com.cody.component.app.activity.BaseActivity;
import com.cody.component.blues.Blues;
import com.cody.component.blues.BluesCallBack;
import com.cody.component.blues.BluesConfig;
import com.cody.component.hybrid.JsBridge;
import com.cody.component.image.ImagePicker;
import com.cody.component.util.ActivityUtil;
import com.cody.component.cat.HttpCat;
import com.cody.component.http.HttpCore;

import java.util.Map;

/**
 * Created by xu.yi. on 2019/4/7.
 * component
 */
public class DemoApplication extends BaseApplication {

    @Override
    public void onInit() {
        super.onInit();
        initBlues();
        ImagePicker.init();
        HttpCore.init(this)
                .withLog(true)
                .withHttpCat(HttpCat.create(this))
//                .withHttpHeader(new HeaderParameterInterceptor())
                .done();
        JsBridge.getInstance()
                .init(BuildConfig.VERSION_NAME, getString(R.string.app_name));
//                .addJsHandler(GbbHandler.class.getSimpleName(), GbbHandler.class);
    }

    private void initBlues() {
        BluesConfig.setAppChannel("CHANNEL_ID");
        BluesConfig.setAppPackageName(BuildConfig.APPLICATION_ID);
        BluesConfig.setAppVersion(BuildConfig.VERSION_NAME);
        BluesConfig.setCrashDebugKey("9d100f49ad");
        BluesConfig.setCrashReleaseKey("588c93f3f2");
        BluesConfig.setDebug(BuildConfig.DEBUG);
        BluesConfig.setTestMode(true);
        BluesConfig.setUserId("test1");
        Blues.install(this, new BluesCallBack() {
            @Override
            public void showException(final String s) {
                Activity activity = ActivityUtil.getCurrentActivity();
                if (activity instanceof BaseActivity) {
                    ((BaseActivity) activity).showToast(s);
                }
            }

            @Override
            public void sameException(final Thread thread, final Throwable throwable) {
//                reStart();
            }

            @Override
            public void fillCrashData(final Map<String, String> map) {
            }
        });
    }

}
