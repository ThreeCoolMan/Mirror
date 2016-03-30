package com.lanou.mirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;

/**
 * Created by Yi on 16/3/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button createBtn;
    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        createBtn = bindView(R.id.activity_login_create_btn);

    }

    @Override
    protected void initData() {
        createBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
}
