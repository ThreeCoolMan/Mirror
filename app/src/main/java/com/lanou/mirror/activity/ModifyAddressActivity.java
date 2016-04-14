package com.lanou.mirror.activity;

import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/11.
 */
public class ModifyAddressActivity extends BaseActivity implements UrlListener, View.OnClickListener {
    private EditText nameEt, numberEt, addressEt;
    private String token, name, number, address, addressId, goodsId;
    private Button btn;

    @Override
    protected int setContent() {
        return R.layout.activity_modifyaddress;
    }

    @Override
    protected void initView() {
        nameEt = bindView(R.id.activity_modifyAddress_name_et);
        numberEt = bindView(R.id.activity_modifyAddress_number_et);
        addressEt = bindView(R.id.activity_modifyAddress_address_et);
        btn = bindView(R.id.activity_modifyAddress_btn_submitAddress);
        btn.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        number = getIntent().getStringExtra("phoneNumber");
        token = getIntent().getStringExtra("token");
        name = getIntent().getStringExtra("addressee");
        address = getIntent().getStringExtra("address");
        addressId = getIntent().getStringExtra("addressId");
        goodsId = getIntent().getStringExtra("goodsId");

        nameEt.setText(name);
        numberEt.setText(number);
        addressEt.setText(address);


    }

    @Override
    public void onClick(View v) {

        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("username", nameEt.getText().toString());
        params.put("cellphone", numberEt.getText().toString());
        params.put("addr_info", addressEt.getText().toString());
        params.put("addr_id", addressId);
        OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(USER_EDIT_ADDRESS_URL, params, new OkHttpNetHelperListener() {
            @Override
            public void requestSucceed(String result, Object bean) {


//               修改地址成功跳转订单页面
                Intent intent = new Intent();
                intent.putExtra("token", token);
                intent.putExtra("goodsId", goodsId);
                setResult(667, intent);
                finish();
            }


            @Override
            public void requestFailed(String cause) {

            }
        });
    }
}
