package com.lanou.mirror.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.LoginBeans;
import com.lanou.mirror.bean.RegisterFailedBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Yi on 16/3/29.
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener, UrlListener, OkHttpNetHelperListener<LoginBeans> {
    private Button createBtn, loginBtn;
    private EditText phoneNumberEt, passWordEt;
    private ImageView closeIv;
    private String phoneNumber, passWord;


    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        Log.d("!!!", "走这?");
        closeIv = bindView(R.id.activity_login_close_iv);
        closeIv.setOnClickListener(this);
        loginBtn = bindView(R.id.activity_login_btn);
        loginBtn.setOnClickListener(this);
        createBtn = bindView(R.id.activity_login_create_btn);
        createBtn.setOnClickListener(this);
        phoneNumber = getIntent().getStringExtra("number");
        phoneNumberEt = bindView(R.id.activity_login_phoneNumber_et);
        phoneNumberEt.setText(phoneNumber);
        passWordEt = bindView(R.id.activity_login_passWord_ed);

        changeButtonBackground(); //判断输入框都有文字登录按钮就变红,输入框无文字就按钮默认灰色

    }

    private void changeButtonBackground() {
        //密码EditText编辑改变监听
        passWordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (phoneNumberEt.length() == 0 || passWordEt.length() == 0) {

                    loginBtn.setBackground(getDrawable(R.mipmap.btn_notavailable));

                } else {
                    loginBtn.setBackground(getDrawable(R.mipmap.btn_login_unpress));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //电话EditText 编辑改变监听
        phoneNumberEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (phoneNumberEt.length() == 0 || passWordEt.length() == 0) {

                    loginBtn.setBackground(getDrawable(R.mipmap.btn_notavailable));

                } else {

                    loginBtn.setBackground(getDrawable(R.mipmap.btn_login_press));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_create_btn:
                Intent intentRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.activity_login_btn:
                passWord = passWordEt.getText().toString();
                phoneNumber = phoneNumberEt.getText().toString();
                if (phoneNumber.equals("") || passWord.equals("")) {
                    Toast.makeText(LoginActivity.this, "请输入登录信息", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("phone_number", phoneNumber);
                    params.put("password", passWord);
                    // OkHttpNetHelper.getOkHttpNetHelper().postRequest(USER_LOGIN_URL, params, LoginBeans.class, this);
                    OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(USER_LOGIN_URL, params, this);
                }
                break;
            case R.id.activity_register_Iv_close:
                finish();
                break;
        }

    }

    @Override
    public void requestSucceed(String result, LoginBeans bean) {

        try {
            final JSONObject object = new JSONObject(result);
            if (object.getString("result").equals("1")) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Toast.makeText(LoginActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void requestFailed(final String cause) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, cause, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
