package com.lanou.mirror.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/5.
 */
public class AddAddressActivity extends BaseActivity implements View.OnClickListener, UrlListener, OkHttpNetHelperListener {
    private Button submitBtn;
    private EditText nameEt, numberEt, addressEt;
    private String token;

    @Override
    protected int setContent() {
        return R.layout.activity_addaddress;
    }

    @Override
    protected void initView() {

        submitBtn = (Button) findViewById(R.id.activity_addAddress_btn_submitAddress);
        submitBtn.setOnClickListener(this);
        nameEt = (EditText) findViewById(R.id.activity_addaddress_name_et);
        numberEt = (EditText) findViewById(R.id.activity_addaddress_number_et);
        addressEt = (EditText) findViewById(R.id.activity_addaddress_address_et);
    }

    @Override
    protected void initData() {
        token = getIntent().getStringExtra("token");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_addAddress_btn_submitAddress:

                String name = nameEt.getText().toString();
                String phoneNumber = numberEt.getText().toString();
                String addressInfo = addressEt.getText().toString();

                if (nameEt.length() > 0 & numberEt.length() > 0 & addressEt.length() > 0) {
                    //提交数据去服务器
                    HashMap<String, String> params = new HashMap<>();
                    params.put("token", token);
                    params.put("username", name);
                    params.put("cellphone", phoneNumber);
                    params.put("addr_info", addressInfo);
                    OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(USER_ADD_ADDRESS_URL, params, this);
                } else {
                    Toast.makeText(AddAddressActivity.this, "请填写详细信息", Toast.LENGTH_SHORT).show();
                }

                break;
        }

        Intent intent = new Intent();
        intent.putExtra("name", nameEt.getText().toString());
        intent.putExtra("number", numberEt.getText().toString());
        intent.putExtra("address", addressEt.getText().toString());
        if (nameEt.getText().length() > 0 && numberEt.getText().length() > 0 && addressEt.getText().length() > 0) {
            setResult(202, intent);
            finish();

        } else {
            Toast.makeText(this, "请完整的填写", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void requestSucceed(String result, Object bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }
        });
        Intent intent = new Intent(AddAddressActivity.this, MyAllAddressActivity.class);
        startActivity(intent);
    }

    @Override
    public void requestFailed(String cause) {

    }
}
