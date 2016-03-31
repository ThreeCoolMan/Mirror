package com.lanou.mirror.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.VerticalPagerAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.fragment.FragmentAllBrowsing;
import com.lanou.mirror.tools.VerticalPager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private VerticalPager verticalPager;
    private ArrayList<Fragment> data;
    private VerticalPagerAdapter adapter;

    @Override
    protected int setContent() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        verticalPager = bindView(R.id.verticalpager);



        }

    @Override
    protected void initData() {
        data = new ArrayList<>();
        for (int i = 0;i < 5;i++){

            data.add(new FragmentAllBrowsing());
        }

        adapter = new VerticalPagerAdapter(getSupportFragmentManager(),data);
        verticalPager.setAdapter(adapter);
        Intent intent = getIntent();
        int list = intent.getIntExtra("list", 0);

        verticalPager.setCurrentItem(list);


    }
}
