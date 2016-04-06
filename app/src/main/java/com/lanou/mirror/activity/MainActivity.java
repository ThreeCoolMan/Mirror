package com.lanou.mirror.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.VerticalPagerAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.fragment.AllBrowsingFragment;
import com.lanou.mirror.tools.VerticalPager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private VerticalPager verticalPager;
    private ArrayList<Fragment> data;
    private VerticalPagerAdapter adapter;
    private TextView loginTv;//登录
    private ImageView logoIv;//商标

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        verticalPager = bindView(R.id.verticalpager);
        loginTv = bindView(R.id.activity_main_login_tv);
        logoIv = bindView(R.id.activity_main_logo_iv);
        //登录
        loginTv.setOnClickListener(this);
        //logo动画
        logoIv.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AllBrowsingFragment fragmentAllBrowsing = new AllBrowsingFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            fragmentAllBrowsing.setArguments(bundle);
            data.add(fragmentAllBrowsing);

        }

        adapter = new VerticalPagerAdapter(getSupportFragmentManager(), data);
        verticalPager.setAdapter(adapter);
        Intent intent = getIntent();
        int list = intent.getIntExtra("list", 0);
        verticalPager.setCurrentItem(list);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_login_tv:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_main_logo_iv:
                ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.2f, 1.0f, 1.1f, 1.0f).setDuration(500).start();
                break;

        }
    }
}
