package com.lanou.mirror.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

import com.facebook.drawee.view.SimpleDraweeView;
import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.WelcomeBean;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/8.
 */
public class WelcomeActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<WelcomeBean> {
    private SimpleDraweeView welcomeIv;
    private Handler handler = new Handler();
    private final long SPLASH_LENGTH = 3000;

    private SharedPreferences preferences;

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
        SharedPreferences preferences = getSharedPreferences("imgUrl", Context.MODE_PRIVATE);
        String url = preferences.getString("url","");
        Log.d("走没",url+"url");
        if (!url.equals("")){
            Log.d("走没","用到缓存");
            welcomeIv.setImageURI(Uri.parse(url));
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_LENGTH);
        }else {

            HashMap<String, String> params = new HashMap<>();
            params.put("version", "1.0.1");
            OkHttpNetHelper.getOkHttpNetHelper().postRequest(START_IMAGE_URL, params, WelcomeBean.class, this);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_LENGTH);
        }




    }


    @Override
    public void requestSucceed(String result, final WelcomeBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d("走没","第一次进来");
                String url = bean.getImg();
                SharedPreferences preferences = getSharedPreferences("imgUrl", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("url",url);
                editor.commit();
                welcomeIv.setImageURI(Uri.parse(url));




            }
        });


    }


    @Override
    public void requestFailed(String cause) {

    }
}
