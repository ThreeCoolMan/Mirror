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
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by Yi on 16/3/29.
 */


public class LoginActivity extends BaseActivity implements View.OnClickListener, UrlListener, OkHttpNetHelperListener<LoginBeans> {
    private Button createBtn, loginBtn;
    private EditText phoneNumberEt, passWordEt;
    private ImageView closeIv, blogIv;
    private String phoneNumber, passWord;
    private String token;
    private String uid;


    @Override
    protected int setContent() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        blogIv = bindView(R.id.activity_login_iv_blog);
        blogIv.setOnClickListener(this);
        closeIv = bindView(R.id.activity_myAlladdress_close_iv);
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
                    OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(USER_LOGIN_URL, params, this);
                }
                break;
            case R.id.activity_register_Iv_close:
                finish();
                break;
            case R.id.activity_login_iv_blog:
                //第三方登录
                ShareSDK.initSDK(v.getContext());
                Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                if (platform.isAuthValid()) {
                    platform.removeAccount();
                }
                platform.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        //在这里获取用户名和头像
                        String head = platform.getDb().getUserIcon();//获取的头像是 URL 的头像地址
                        String name = platform.getDb().getUserName();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("userName", name);
                        intent.putExtra("userHead", head);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                platform.SSOSetting(false);
                platform.showUser(null);
                break;
        }

    }

    @Override
    public void requestSucceed(String result, LoginBeans bean) {

        try {
            final JSONObject object = new JSONObject(result);
            if (object.getString("result").equals("1")) { //登录成功跳转主页面传值 token 和 uid
                if (object.has("data")) {
                    JSONObject obj = object.getJSONObject("data");
                    token = obj.getString("token");
                    uid = obj.getString("uid");

                }

                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("uid", uid);
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
