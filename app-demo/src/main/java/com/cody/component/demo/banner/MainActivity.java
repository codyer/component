/*
 * ************************************************************
 * 文件：MainActivity.java  模块：app-demo  项目：component
 * 当前修改时间：2021年02月27日 15:30:04
 * 上次修改时间：2021年02月27日 15:09:40
 * 作者：Cody.yi   https://github.com/codyer
 *
 * 描述：app-demo
 * Copyright (c) 2021
 * ************************************************************
 */

package com.cody.component.demo.banner;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import cody.bus.ObserverWrapper;

import com.cody.component.bind.CoreBR;
import com.cody.component.demo.LiveEventBus;
import com.cody.component.demo.R;
import com.cody.component.app.activity.StaticActivity;
import com.cody.component.banner.adapter.BindingBannerAdapter;
import com.cody.component.banner.data.BannerViewData;
import com.cody.component.demo.bean.TestBean;
import com.cody.component.demo.bean.TestDataBean;
import com.cody.component.demo.bus.BusDemoActivity;
import com.cody.component.demo.bus.cody.DemoGroupBus;
import com.cody.component.demo.data.generate.CatApiOpen$RemoteDataSource;
import com.cody.component.demo.data.generate.CatHttpBin$RemoteDataSource;
import com.cody.component.demo.databinding.ActivityMainBannerBinding;
import com.cody.component.demo.databinding.UpdateDialogBinding;
import com.cody.component.hybrid.activity.HtmlActivity;
import com.cody.component.image.certificate.camera.CameraActivity;
import com.cody.component.image.scan.ScanActivity;
import com.cody.component.demo.list.ListTestActivity;
import com.cody.component.update.UpdateViewData;
import com.cody.component.util.ActivityUtil;
import com.cody.component.cat.HttpCat;
import com.cody.component.http.HttpCore;
import com.cody.component.http.callback.RequestCallback;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends StaticActivity<ActivityMainBannerBinding> {

    @Override
    protected int getLayoutID() {
        return R.layout.activity_main_banner;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toBus:

                com.cody.component.demo.LiveEventBus.getDefault("Event1",String.class).setValue("推送数据 原始数据");
                startActivity(new Intent(MainActivity.this, BusDemoActivity.class));
                break;
            case R.id.httpRequest1:
//                ActivityUtil.navigateTo(FriendlyActivity.class);
//                testDialog();
                httpRequest1();
                break;
            case R.id.httpRequest2:
                httpRequest2();
                break;
            case R.id.hideCat:
                HttpCat.getInstance().hide();
                break;
            case R.id.showCat:
                HttpCat.getInstance().showWithNotification();
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
            case R.id.scan:
                ScanActivity.openScanActivity();
                break;
        }
    }

    @Override
    protected void onBaseReady(Bundle savedInstanceState) {
        super.onBaseReady(savedInstanceState);
        final List<BannerViewData> banners = new ArrayList<>();
        banners.add(new BannerViewData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F911%2F101416132957%2F161014132957-9.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1636386194&t=23f880115ab992812c4b550ee6212a9e"));
        banners.add(new BannerViewData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1207%2F0944%2Fntk111703.jpg&refer=http%3A%2F%2Fimg2.niutuku.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1636386194&t=9d34950bd0273601a852ca89ca8d0f18"));
        banners.add(new BannerViewData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.33lc.com%2Farticle%2FUploadPic%2F2012-7%2F201272614275728076.jpg&refer=http%3A%2F%2Fwww.33lc.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1636386265&t=bddb9f5ce9017f5a83ebf4a5ce3ad3f0"));
        banners.add(new BannerViewData("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic.jj20.com%2Fup%2Fallimg%2F1113%2F051R0115229%2F20051Q15229-11-1200.jpg&refer=http%3A%2F%2Fpic.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1636386265&t=641d7e71a5b73c4c8cc49c042ff15198"));
        BindingBannerAdapter<BannerViewData> bannerAdapter = new BindingBannerAdapter<>(this);
        bannerAdapter.setItemClickListener((parent, view, position, id) -> Toast.makeText(MainActivity.this, "position=" + position, Toast.LENGTH_SHORT).show());
        bannerAdapter.submitList(banners);
        getBinding().banner.setBindingBannerAdapter(bannerAdapter);
        DemoGroupBus.testBean()
                .observe(MainActivity.this, new ObserverWrapper<TestBean>() {
                    @Override
                    public void onChanged(TestBean testBean) {
                        Toast.makeText(MainActivity.this, "首页事件监听" + testBean.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void testDialog() {
        UpdateViewData viewData = new UpdateViewData();
        viewData.setApkSize("12.4M");
        viewData.setNewVersion("1.2.4");
        viewData.setUpdateTime("2019-6-4 19:11");
        viewData.setUpdateInfo("1.修复了但是当发生的方式发呆\n\n2.水电费水电费和扫黄打非");
        UpdateDialogBinding binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.update_dialog, null, false);
        binding.setVariable(CoreBR.onClickListener, (View.OnClickListener) v -> {
            switch (v.getId()){
                case android.R.id.button1:
                    showToast("updateNow");
                    break;
                case android.R.id.button2:
                    showToast("notNow");
                    break;
            }
        });
        binding.setVariable(CoreBR.viewData, viewData);
        new AlertDialog.Builder(this)
                .setView(binding.getRoot())
                .setCancelable(true).show();
    }

    /**
     * 身份证正面
     */
    public void frontIdCard() {
        CameraActivity.openCameraActivity(this, CameraActivity.TYPE_ID_CARD_FRONT);
    }

    /**
     * 身份证反面
     */
    public void backIdCard() {
        CameraActivity.openCameraActivity(this, CameraActivity.TYPE_ID_CARD_BACK);
    }

    /**
     * 营业执照水平
     */
    public void companyH() {
        CameraActivity.openCameraActivity(this, CameraActivity.TYPE_BUSINESS_LICENSE_LANDSCAPE);
    }

    /**
     * 营业执照垂直
     */
    public void companyV() {
        CameraActivity.openCameraActivity(this, CameraActivity.TYPE_BUSINESS_LICENSE_PORTRAIT);
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
        String result = ScanActivity.getScanResult(requestCode, resultCode, data);
        if (!TextUtils.isEmpty(result)) {
            if (URLUtil.isValidUrl(result)) {
                HtmlActivity.startHtml("扫码打开", result);
            } else {
                showToast(result);
            }
        }
    }

    private void httpRequest1() {
        CatHttpBin$RemoteDataSource api = new CatHttpBin$RemoteDataSource(null);
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
        CatApiOpen$RemoteDataSource api = new CatApiOpen$RemoteDataSource(null);
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
