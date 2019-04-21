/*
 * ************************************************************
 * 文件：MainActivity.java  模块：app  项目：component
 * 当前修改时间：2019年04月13日 08:43:54
 * 上次修改时间：2019年04月12日 21:41:40
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app
 * Copyright (c) 2019
 * ************************************************************
 */

package com.cody.component.banner;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cody.component.R;
import com.cody.component.adapter.list.OnBindingItemClickListener;
import com.cody.component.app.activity.EmptyBindActivity;
import com.cody.component.banner.adapter.BindingBannerAdapter;
import com.cody.component.banner.data.BannerViewData;
import com.cody.component.bean.TestDataBean;
import com.cody.component.bus.BusDemoActivity;
import com.cody.component.data.remote.CatApiOpen$DataSource;
import com.cody.component.data.remote.CatHttpBin$DataSource;
import com.cody.component.databinding.ActivityMainBannerBinding;
import com.cody.component.hybrid.activity.HtmlActivity;
import com.cody.component.image.certificate.camera.CameraActivity;
import com.cody.component.list.ListTestActivity;
import com.cody.component.util.ActivityUtil;
import com.cody.http.cat.HttpCat;
import com.cody.http.core.HttpCore;
import com.cody.http.core.callback.RequestCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends EmptyBindActivity<ActivityMainBannerBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_banner;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toBus:
                startActivity(new Intent(MainActivity.this, BusDemoActivity.class));
                break;
            case R.id.httpRequest1:
                httpRequest1();
                break;
            case R.id.httpRequest2:
                httpRequest2();
                break;
            case R.id.hideCat:
                HttpCat.getInstance().hide();
                break;
            case R.id.showCat:
                HttpCat.getInstance().show();
                break;
            case R.id.muteCat:
                HttpCat.getInstance().mute();
                break;
            case R.id.killCat:
                HttpCore.getInstance().killHttpCat().done();
                break;
            case R.id.openHtml:
                HtmlActivity.startHtml("test", "description", "https://www.baidu.com", true, true);
                break;
            case R.id.testList:
                ActivityUtil.navigateTo(ListTestActivity.class);
                break;
            case R.id.testFront:
                frontIdCard();
                break;
            case R.id.testBehind:
                backIdCard();
                break;
            case R.id.testCompanyV:
                companyV();
                break;
            case R.id.testCompanyH:
                companyH();
                break;
        }
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);

        final List<BannerViewData> banners = new ArrayList<>();
        banners.add(new BannerViewData("http://img.zcool.cn/community/01c98059093a88a801214550cd0853.jpg"));
        banners.add(new BannerViewData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554377914890&di=7ab78e69f3fec25509f88cd70171db38&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01a82c5711fd566ac7251343a63c56.jpg%40900w_1l_2o_100sh.jpg"));
        banners.add(new BannerViewData("http://img.zcool.cn/community/01c98059093a88a801214550cd0853.jpg"));
        banners.add(new BannerViewData("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554377856503&di=eddca6d3c83a95e183dfb7c073d14b69&imgtype=0&src=http%3A%2F%2Fpic23.nipic.com%2F20120814%2F8758059_141413668315_2.jpg"));
        BindingBannerAdapter bannerAdapter = new BindingBannerAdapter(this);
        bannerAdapter.setItemClickListener(new OnBindingItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        bannerAdapter.submitList(banners);
        getBinding().banner.setBindingBannerAdapter(bannerAdapter);
    }

    /**
     * 身份证正面
     */
    public void frontIdCard() {
        CameraActivity.toCameraActivity(this, CameraActivity.TYPE_IDCARD_FRONT);
    }

    /**
     * 身份证反面
     */
    public void backIdCard() {
        CameraActivity.toCameraActivity(this, CameraActivity.TYPE_IDCARD_BACK);
    }
    /**
     * 营业执照水平
     */
    public void companyH() {
        CameraActivity.toCameraActivity(this, CameraActivity.TYPE_COMPANY_LANDSCAPE);
    }

    /**
     * 营业执照垂直
     */
    public void companyV() {
        CameraActivity.toCameraActivity(this, CameraActivity.TYPE_COMPANY_PORTRAIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CameraActivity.REQUEST_CODE && resultCode == CameraActivity.RESULT_CODE) {
            //获取图片路径，显示图片
            final String path = CameraActivity.getImagePath(data);
            if (!TextUtils.isEmpty(path)) {
                getBinding().identify.setImageBitmap(BitmapFactory.decodeFile(path));
            }
        }
    }

    private void httpRequest1() {
        CatHttpBin$DataSource api = new CatHttpBin$DataSource(null);
        RequestCallback cb = new RequestCallback<Object>() {
            @Override
            public void showToast(String message) {
                Log.e(TAG, message);
            }

            @Override
            public void onSuccess(Object s) {
                showToast(s.toString());
            }
        };
        api.get(cb);
        api.post(new TestDataBean("posted"), cb);
        api.patch(new TestDataBean("patched"), cb);
        api.put(new TestDataBean("put"), cb);
        api.delete(cb);
        api.status(201, cb);
        api.status(401, cb);
        api.status(500, cb);
        api.delay(9, cb);
        api.delay(15, cb);
        api.redirectTo("https://http2.akamai.com", cb);
        api.redirect(3, cb);
        api.redirectRelative(2, cb);
        api.redirectAbsolute(4, cb);
        api.stream(500, cb);
        api.streamBytes(2048, cb);
        api.image("image/png", cb);
        api.gzip(cb);
        api.xml(cb);
        api.utf8(cb);
        api.deflate(cb);
        api.cookieSet("v", cb);
        api.basicAuth("me", "pass", cb);
        api.drip(512, 5, 1, 200, cb);
        api.deny(cb);
        api.cache("Mon", cb);
        api.cache(30, cb);
    }

    private void httpRequest2() {
        CatApiOpen$DataSource api = new CatApiOpen$DataSource(null);
        RequestCallback<String> cb = new RequestCallback<String>() {
            @Override
            public void onSuccess(String s) {
                showToast(s);
            }
        };
        api.singlePoetry(cb);
        api.recommendPoetry(cb);
        api.musicBroadcasting(cb);
        api.novelApi(cb);
    }
}
