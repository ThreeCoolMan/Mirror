package com.lanou.mirror.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.R;

/**
 * Created by dllo on 16/3/29.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Button createBtn;


    @Override
    protected int setContent() {
        return R.layout.activity_register;

    }

    @Override
    protected void initView() {
        createBtn = bindView(R.id.activity_register_btn_creatnumber);
        createBtn.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(RegisterActivity.this, "xixixixi", Toast.LENGTH_SHORT).show();
    }
}
