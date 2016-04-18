package com.lanou.mirror.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.VerticalPagerAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.fragment.AllBrowsingFragment;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.tools.OkHttpNetHelper;
import com.lanou.mirror.tools.T;
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
        verticalPager = bindView(R.id.verticalPager);
        loginTv = bindView(R.id.activity_main_login_tv);
        logoIv = bindView(R.id.activity_main_logo_iv);
        //登录
        loginTv.setOnClickListener(this);
        //logo动画
        logoIv.setOnClickListener(this);
    }


    @Override
    protected void initData() {
        token = getSharedPreferences("loginUser", MODE_PRIVATE).getString("token", null);

        if (token != null && token != "") {
            loginTv.setText("购物车");
        }
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


        OkHttpNetHelper.getOkHttpNetHelper().postRequest("http://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=狗&step_word=&pn=1&spn=0&di=99000601150&pi=&rn=1&tn=baiduimagedetail&is=&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2172041622%2C3819068822&os=3482871129%2C3554888365&simid=429856%2C664502570&adpicid=0&ln=1000&fr=&fmq=1460723505529_R_D&fm=detail&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=10&oriquery=&objurl=http%3A%2F%2Fpic4.nipic.com%2F20091118%2F3047572_163336087407_2.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bgtrtv_z%26e3Bv54AzdH3Ffi5oAzdH3F8AzdH3Fm9AzdH3Fwk9vkjwluvmw9dld_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0", new OkHttpNetHelperListener() {
            @Override
            public void requestSucceed(String result, Object bean) {
                Looper.prepare();
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }

            @Override
            public void requestFailed(String cause) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_login_tv:

                if (token == null || token == "") {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    break;
                }
                if (token != null) {

                    break;
                }
            case R.id.activity_main_logo_iv:
                //每设置一次变化一次
                ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.3f, 1.0f, 1.2f, 1.0f).setDuration(500).start();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {

        if ((System.currentTimeMillis() - exitTime) > 2000) {
            T.showShort(MainActivity.this, "特么的别走");
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);//退出当前程序
        }
    }


}
