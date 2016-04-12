package com.lanou.mirror.activity;


import android.content.Intent;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.MyAllAddressAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.MyAllAddressBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;
import com.lanou.mirror.tools.SwipeListView;
import com.lanou.mirror.tools.T;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Yi on 16/4/8.
 */


public class MyAllAddressActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<MyAllAddressBeans> {

    private SwipeListView listView;
    private MyAllAddressAdapter adapter;
    private TextView addTv;
    private ImageView closeIv;
    private String token = "";
    private String addrId = "";
    private String goodsId = "";


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

        goodsId = getIntent().getStringExtra("goodsId");
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
                adapter = new MyAllAddressAdapter((ArrayList<MyAllAddressBeans.DataEntity.ListEntity>) bean.getData().getList(), MyAllAddressActivity.this, listView.getRightViewWidth(), token, goodsId);

                adapter.setOnRightItemClickListener(new MyAllAddressAdapter.onRightItemClickListener() {

                    //删除监听
                    @Override
                    public void onRightItemClick(View v, int position) {
                        //刷新适配器
                        addrId = bean.getData().getList().get(position).getAddr_id();
                        bean.getData().getList().remove(position);
                        adapter.setData((ArrayList<MyAllAddressBeans.DataEntity.ListEntity>) bean.getData().getList());
                        listView.setAdapter(adapter);

                        //删除数据请求
                        HashMap<String, String> paramsDelete = new HashMap<String, String>();
                        paramsDelete.put("token", token);
                        paramsDelete.put("addr_id", addrId);
                        OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(UESR_DELETE_ADDRESS_URL, paramsDelete, new OkHttpNetHelperListener() {
                            @Override
                            public void requestSucceed(final String result, Object bean) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                });
                            }

                            @Override
                            public void requestFailed(String cause) {

                            }
                        });
                    }
                });
                listView.setAdapter(adapter);

                //点击item的监听
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        HashMap<String, String> map = new HashMap<String, String>();
                        //去服务器提交新的默认地址让其更改
                        map.put("token", token);
                        map.put("addr_id", bean.getData().getList().get(position).getAddr_id());//TODO 获得子布局的addId；

                        OkHttpNetHelper.getOkHttpNetHelper().postStringRequest(UESR_DEFAULT_ADRESS_URL, map, new OkHttpNetHelperListener() {
                            @Override
                            public void requestSucceed(String result, Object bean) {
                                Log.d("这是什么", addrId);
                                Log.d("resultMyAll", result);
                                Intent intent = new Intent(MyAllAddressActivity.this, BuyDetailsActivity.class);
                                intent.putExtra("goodsId", goodsId);
                                intent.putExtra("token", token);
                                startActivity(intent);
                            }

                            @Override
                            public void requestFailed(String cause) {

                            }
                        });
                    }
                });

            }
        });

    }

    @Override
    public void requestFailed(String cause) {

    }


}
