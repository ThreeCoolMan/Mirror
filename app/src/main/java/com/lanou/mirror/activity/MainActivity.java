package com.lanou.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
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
    private TextView loginTv;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        verticalPager = bindView(R.id.verticalpager);
        loginTv = bindView(R.id.activity_main_login_tv);
        //登录
        loginTv.setOnClickListener(this);
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
        }
    }
}
