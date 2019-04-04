package com.cody.component.banner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cody.component.adapter.list.OnBindingItemClickListener;

import java.util.ArrayList;
import java.util.List;

import com.cody.component.R;
import com.cody.component.bus.BusDemoActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_banner);
        BindingBanner banner = findViewById(R.id.banner);

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
        banner.setBindingBannerAdapter(bannerAdapter);

        findViewById(R.id.toBus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BusDemoActivity.class));
            }
        });
    }
}
