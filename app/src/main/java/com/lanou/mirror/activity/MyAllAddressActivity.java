package com.lanou.mirror.activity;


import android.widget.ListView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.MyAllAddressAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.MyAllAddressBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Yi on 16/4/8.
 */


public class MyAllAddressActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<MyAllAddressBeans> {

    private ListView listView;
    private MyAllAddressAdapter adapter;

    private String token = "";


    @Override

    protected int setContent() {
        return R.layout.activity_myalladdress;
    }

    @Override
    protected void initView() {
        listView = bindView(R.id.activity_myaddress_listview);

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
                adapter = new MyAllAddressAdapter((ArrayList<MyAllAddressBeans.DataEntity.ListEntity>) bean.getData().getList(), MyAllAddressActivity.this);
                listView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void requestFailed(String cause) {

    }
}
