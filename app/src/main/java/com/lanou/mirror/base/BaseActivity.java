package com.lanou.mirror.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Yi on 16/3/29.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContent());
        initView();
        initData();
    }

    protected abstract int setContent();//绑定布局

    protected abstract void initView();//绑定组件

    protected abstract void initData();//加载数据

    protected <T extends View> T bindView(int id) {
        return (T) findViewById(id);
    }


}
