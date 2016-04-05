package com.lanou.mirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.R;
import com.lanou.mirror.bean.RegisterFailedBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dllo on 16/3/29.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener, UrlListener, OkHttpNetHelperListener {
    private EditText phoneNumberEd, verificationCodeEd, passWordEd;
    private Button createBtn, sendBtn;
    private String number, verificationCode, passWord;
    private ImageView closeIv;
    private HashMap<String, String> params;


    @Override
    protected int setContent() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        closeIv = bindView(R.id.activity_register_Iv_close);
        closeIv.setOnClickListener(this);
        verificationCodeEd = bindView(R.id.activity_register_et_verification);

        phoneNumberEd = bindView(R.id.activity_register_et_phonenumber);
        passWordEd = bindView(R.id.activity_register_et_password);

        sendBtn = bindView(R.id.activity_register_btn_send_verification);
        sendBtn.setOnClickListener(this);
        createBtn = bindView(R.id.activity_register_btn_creatnumber);
        createBtn.setOnClickListener(this);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_btn_creatnumber://注册
                passWord = passWordEd.getText().toString();
                number = phoneNumberEd.getText().toString();
                verificationCode = verificationCodeEd.getText().toString();
                if (!number.equals("") && !verificationCode.equals("") && !passWord.equals("")) {
                    params = new HashMap<>();
                    params.put("phone_number", number);
                    params.put("number", verificationCode);
                    params.put("password", passWord);
                    OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(USER_REGISTER_URL, params, this);

                } else {
                    Toast.makeText(RegisterActivity.this, "请认真填写信息", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_register_btn_send_verification://请求验证码
                number = phoneNumberEd.getText().toString();
                if (!number.equals("")) {
                    params = new HashMap<>();
                    params.put("phone number", number);
                    OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(USER_SEND_CODE_URL, params, this);
                    Toast.makeText(RegisterActivity.this, "验证码发送成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.activity_register_Iv_close:
                finish();
        }
    }

    @Override
    public void requestSucceed(String result, final Object bean) {

        try {
            JSONObject object = new JSONObject(result);
            if (object.get("data").equals("") && !object.get("msg").equals("")) {
                final RegisterFailedBeans beans = new RegisterFailedBeans();
                beans.setResult(object.getString("result"));
                beans.setMsg(object.getString("msg"));
                beans.setData(object.getString("data"));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, beans.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else if (!object.get("data").equals("")) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.putExtra("number", number);
                            startActivity(intent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
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
                Toast.makeText(RegisterActivity.this, cause, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
