package com.lanou.mirror.activity;

import android.support.v4.app.Fragment;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.VerticalPagerAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.fragment.FragmentAllBrowsing;
import com.lanou.mirror.fragment.FragmentSpectacles;
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
        data.add(new FragmentAllBrowsing());
        data.add(new FragmentSpectacles());
        adapter = new VerticalPagerAdapter(getSupportFragmentManager(),data);
        verticalPager.setAdapter(adapter);

    }
}
