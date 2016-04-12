package com.lanou.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.bean.WelcomeBean;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/8.
 */
public class WelcomeActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<WelcomeBean> {
    private ImageView welcomeIv;
    private Handler handler = new Handler();
    private final long SPLASH_LENGTH = 3000;


    @Override
    protected int setContent() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        welcomeIv = bindView(R.id.activity_welcome_iv);
    }

    @Override
    protected void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("version", "1.0.1");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(START_IMAGE_URL, params, WelcomeBean.class, this);

    }


    @Override
    public void requestSucceed(String result, final WelcomeBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = bean.getImg();
                Log.d("aaaaaaa", url + "");
//                OkHttpNetHelper.getOkHttpNetHelper().setOkImage("http://pic1.zhimg.com/e1cc747cbf2076a378d2fe0f8c3b2e20.jpg", welcomeIv);
            }
        });

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH);
    }

    @Override
    public void requestFailed(String cause) {

    }
}
