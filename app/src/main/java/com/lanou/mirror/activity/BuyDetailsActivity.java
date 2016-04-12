package com.lanou.mirror.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.AddressListBeans;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.bean.OrderBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/1.
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener, UrlListener, OkHttpNetHelperListener<AddressListBeans> {
    private TextView writeAddressTv, nameTv, numberTv, addressTv, goodsNameTv, goodsContentTv, goodsPriceTv;
    private String token;
    private int defaultPosition;//默认的地址所在集合的位置
    private String goodsId;//商品 Id
    private ImageView closeIv, goodsIv;

    @Override
    protected int setContent() {
        return R.layout.activity_buydetails;
    }

    @Override
    protected void initView() {
        goodsIv = bindView(R.id.activity_buyDetails_iv_goods);
        goodsPriceTv = bindView(R.id.activity_buyDetails_tv_goodsPrice);
        goodsContentTv = bindView(R.id.activity_buyDetails_tv_goodsContent);
        goodsNameTv = bindView(R.id.activity_buyDetails_tv_goodsName);
        closeIv = bindView(R.id.activity_buyDetails_iv_close);
        closeIv.setOnClickListener(this);
        writeAddressTv = (TextView) findViewById(R.id.activity_buydetails_writeaddress_tv);
        nameTv = (TextView) findViewById(R.id.activity_buydetails_name);
        numberTv = (TextView) findViewById(R.id.activity_buydetails_number);
        addressTv = (TextView) findViewById(R.id.activity_buydetails_address);
        writeAddressTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        token = getIntent().getStringExtra("token");
        goodsId = getIntent().getStringExtra("goodsId");

        //地址列表请求
        HashMap<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("device_type", "3");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(USER_ADDRESS_LIST_URL, params, AddressListBeans.class, this);

        //商品请求
        HashMap<String, String> paramsOrder = new HashMap<>();
        paramsOrder.put("token", token);
        paramsOrder.put("device_type", "3");
        paramsOrder.put("goods_id", goodsId);
        paramsOrder.put("goods_num", "1");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(ORDER_SUB_URL, paramsOrder, OrderBeans.class, new OkHttpNetHelperListener<OrderBeans>() {

            @Override
            public void requestSucceed(String result, final OrderBeans bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        goodsNameTv.setText(bean.getData().getGoods().getGoods_name());
                        goodsContentTv.setText(bean.getData().getGoods().getBook_copy());
                        goodsPriceTv.setText(bean.getData().getGoods().getPrice());
                        OkHttpNetHelper.getOkHttpNetHelper().setOkImage(bean.getData().getGoods().getPic(), goodsIv);
                    }
                });
            }

            @Override
            public void requestFailed(String cause) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_buydetails_writeaddress_tv:
                //没有默认地址点击跳转添加地址页面
                if (writeAddressTv.getText().toString().equals(getResources().getString(R.string.activity_buydetails_writeaddress))) {
                    Intent intent = new Intent(BuyDetailsActivity.this, AddAddressActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                } else {//有默认地址就跳转我的所有地址页面
                    Intent intent = new Intent(BuyDetailsActivity.this, MyAllAddressActivity.class);
                    intent.putExtra("token", token);
                    startActivity(intent);
                }
                break;
            case R.id.activity_buyDetails_iv_close:
                finish();
                break;
        }
    }

    @Override
    public void requestSucceed(String result, final AddressListBeans bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (bean.getData().getList().size() == 0) {//判断如果没有保存的地址

                    nameTv.setText("请设置默认地址");
                    writeAddressTv.setText(R.string.activity_buydetails_writeaddress);

                } else {//如果有已经保存的地址
                    for (int i = 0; i < bean.getData().getList().size(); i++) {
                        if (bean.getData().getList().get(i).getIf_moren().equals("1")) {
                            defaultPosition = i;//获取默认地址所在实体类里的集合位置
                            break;
                        }
                    }
                    //设置显示以保存的地址
                    String addressee = bean.getData().getList().get(defaultPosition).getUsername();
                    String addressId = bean.getData().getList().get(defaultPosition).getAddr_id();
                    String addressPhone = bean.getData().getList().get(defaultPosition).getCellphone();
                    String addressInfo = bean.getData().getList().get(defaultPosition).getAddr_info();

                    nameTv.setText("收件人: " + addressee);
                    numberTv.setText("电话: " + addressPhone);
                    numberTv.setVisibility(View.VISIBLE);
                    addressTv.setText("地址: " + addressInfo);
                    addressTv.setVisibility(View.VISIBLE);
                    writeAddressTv.setText(R.string.activity_buyDetails_writeAdressTV_text_changeAddress);
                }
            }
        });
    }

    @Override
    public void requestFailed(String cause) {

    }
}
