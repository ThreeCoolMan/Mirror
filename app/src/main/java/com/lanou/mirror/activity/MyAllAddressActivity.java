package com.lanou.mirror.activity;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
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
    private String addrId = "182";


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
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(MyAllAddressActivity.this,BuyDetailsActivity.class);
                        HashMap<String,String> map = new HashMap<String, String>();
                        //去服务器提交新的默认地址让其更改
                        map.put("token",token);
                        map.put("addr_id",addrId);
                        intent.putExtra("token",token);
                        OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(UESR_DEFAULT_ADRESS_URL, map, new OkHttpNetHelperListener() {
                            @Override
                            public void requestSucceed(String result, Object bean) {

                            }

                            @Override
                            public void requestFailed(String cause) {

                            }
                        });
                        startActivity(intent);

                    }
                });
            }
        });

    }

    @Override
    public void requestFailed(String cause) {

    }

}
