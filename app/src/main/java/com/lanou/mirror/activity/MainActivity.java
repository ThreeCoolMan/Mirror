package com.lanou.mirror.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private String token = "";//服务器获取得用户凭证
    private long exitTime = 0;

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

        token = getIntent().getStringExtra("token");
        data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            AllBrowsingFragment fragmentAllBrowsing = new AllBrowsingFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", i);
            bundle.putString("token", token);
            fragmentAllBrowsing.setArguments(bundle);
            data.add(fragmentAllBrowsing);

        }

        adapter = new VerticalPagerAdapter(getSupportFragmentManager(), data);
        verticalPager.setAdapter(adapter);
        //从popupwindow的adapter传过来的
        Intent intent = getIntent();
        int list = intent.getIntExtra("list", 0);
        //设置跳转到的几个页面
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
                //每设置一次变化一次
                ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.3f, 1.0f, 1.2f, 1.0f).setDuration(500).start();
                break;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }


        return super.onKeyDown(keyCode, event);
    }

    private void exit() {

        if ((System.currentTimeMillis()- exitTime)>2000){
           showShortToast("特么的别走");
            exitTime = System.currentTimeMillis();
        }else {
            finish();
            System.exit(0);//退出当前程序
        }
    }
}
