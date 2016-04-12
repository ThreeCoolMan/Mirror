package com.lanou.mirror.activity;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.MyAllAddressAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.MyAllAddressBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;


/**
 * Created by Yi on 16/4/8.
 */


public class MyAllAddressActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<MyAllAddressBeans> {

    private ListView listView;
    private MyAllAddressAdapter adapter;
    private TextView addTv;
    private ImageView closeIv;
    private String token = "";


    @Override
    protected int setContent() {
        return R.layout.activity_myalladdress;
    }

    @Override
    protected void initView() {
        closeIv = bindView(R.id.activity_myAlladdress_close_iv);
        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView = bindView(R.id.activity_myaddress_listview);
        addTv = bindView(R.id.activity_myAddress_tv_add);
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAllAddressActivity.this, AddAddressActivity.class);
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        token = getIntent().getStringExtra("token");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("device_type", "3");
        params.put("page", "");

        OkHttpNetHelper.getOkHttpNetHelper().postRequest(USER_ADDRESS_LIST_URL, params, MyAllAddressBeans.class, this);
    }

    @Override
    public void requestSucceed(String result, final MyAllAddressBeans bean) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new MyAllAddressAdapter(bean.getData().getList(), MyAllAddressActivity.this);
                listView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void requestFailed(String cause) {

    }


}
