package com.lanou.mirror.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.demo.H5PayDemoActivity;
import com.alipay.sdk.pay.demo.PayResult;
import com.alipay.sdk.pay.demo.SignUtils;
import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.AddressListBeans;
import com.lanou.mirror.bean.AilPayBeans;
import com.lanou.mirror.bean.OrderBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;
import com.lanou.mirror.tools.T;

import java.util.HashMap;

/**
 * Created by dllo on 16/4/1.
 */
public class BuyDetailsActivity extends BaseActivity implements View.OnClickListener, UrlListener, OkHttpNetHelperListener<AddressListBeans> {
    private TextView writeAddressTv, nameTv, numberTv, addressTv, goodsNameTv, goodsContentTv, goodsPriceTv;
    private String token = "";
    private int defaultPosition;//默认的地址所在集合的位置
    private String goodsId;//商品 Id
    private ImageView closeIv, goodsIv;
    private String addressee, addressPhone, addressInfo;
    private Button orderBtn;
    private String str = "";//支付宝支付请求的账户协议密匙等信息
    private final static int SDK_PAY_FLAG = 1;
    public static String RSA_PRIVATE = "";// 商户私钥，pkcs8格式
    private String orderId;//订单 id
    private String addressId;//地址 id
    private String goodsName;

    @Override
    protected int setContent() {
        return R.layout.activity_buydetails;
    }

    @Override
    protected void initView() {
        orderBtn = bindView(R.id.activity_buyDetails_confirmOrder_btn);
        orderBtn.setOnClickListener(this);
        goodsIv = bindView(R.id.activity_buyDetails_iv_goods);
        goodsPriceTv = bindView(R.id.activity_buyDetails_tv_goodsPrice);
        goodsContentTv = bindView(R.id.activity_buyDetails_tv_goodsContent);
        goodsNameTv = bindView(R.id.activity_buyDetails_tv_goodsName);
        closeIv = bindView(R.id.activity_buyDetails_iv_close);
        closeIv.setOnClickListener(this);
        writeAddressTv = (TextView) findViewById(R.id.activity_buyDetails_writeAddress_tv);
        nameTv = (TextView) findViewById(R.id.activity_buyDetails_name);
        numberTv = (TextView) findViewById(R.id.activity_buyDetails_number);
        addressTv = (TextView) findViewById(R.id.activity_buyDetails_address);
        writeAddressTv.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        try {

            token = getIntent().getStringExtra("token");
            goodsId = getIntent().getStringExtra("goodsId");
        } catch (Exception e) {

        }

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
                orderId = bean.getData().getOrder_id();
                addressId = bean.getData().getAddress().getAddr_id();
                goodsName = bean.getData().getGoods().getGoods_name();
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
                T.showShort(BuyDetailsActivity.this, cause);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_buyDetails_writeAddress_tv:
                //没有默认地址点击跳转添加地址页面
                if (writeAddressTv.getText().toString().equals(getResources().getString(R.string.activity_buyDetails_writeAddress))) {
                    Intent intent = new Intent(BuyDetailsActivity.this, AddAddressActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("goodsId", goodsId);
                    startActivity(intent);
                } else {//有默认地址就跳转我的所有地址页面
                    Intent intent = new Intent(BuyDetailsActivity.this, MyAllAddressActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("goodsId", goodsId);
                    startActivity(intent);
                }
                break;
            case R.id.activity_buyDetails_iv_close:
                finish();
                break;
            case R.id.activity_buyDetails_confirmOrder_btn:
                //弹出 dialog下订单请求
                showDiaLog();
                break;
        }
    }

    private void showDiaLog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //dialog 载入布局
        View view = LayoutInflater.from(this).inflate(R.layout.activity_orderdetail_sure_dialog, null);
        builder.setView(view);
        //支付宝支付监听
        view.findViewById(R.id.dialog_orderDetail_ailPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> paramsAilPay = new HashMap<>();
                paramsAilPay.put("token", token);
                paramsAilPay.put("order_no", orderId);
                paramsAilPay.put("addr_id", addressId);
                paramsAilPay.put("goodsname", goodsName);
                OkHttpNetHelper.getOkHttpNetHelper().postRequest(PAY_AIL_PAY_SUB_URL, paramsAilPay, AilPayBeans.class, new OkHttpNetHelperListener<AilPayBeans>() {
                    @Override
                    public void requestSucceed(String result, AilPayBeans bean) {
                        if (!bean.getData().getStr().equals("")) {
                            str = bean.getData().getStr();
                            //支付宝支付请求
                            requestAilPay(str);
                        }
                    }

                    @Override
                    public void requestFailed(String cause) {

                    }
                });
            }
        });
        //微信支付监听
        view.findViewById(R.id.dialog_orderDetail_weixinPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        builder.show();
    }

    /**
     * 阿里支付请求
     *
     * @param str 这是请求需要的协议以及账户内容
     */
    private void requestAilPay(String str) {

        String sign = sign(str);

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = str + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(BuyDetailsActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(this, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.meituan.com";
        // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        startActivity(intent);
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }


    //支付宝之后的回调 判断是否支付成功
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(BuyDetailsActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(BuyDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(BuyDetailsActivity.this, "支付失败", Toast.LENGTH_SHORT).show();


                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };


    @Override
    public void requestSucceed(String result, final AddressListBeans bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (bean.getData().getList().size() == 0) {//判断如果没有保存的地址
                    nameTv.setText("请设置默认地址");
                    writeAddressTv.setText(R.string.activity_buyDetails_writeAddress);


                } else {//如果有已经保存的地址
                    for (int i = 0; i < bean.getData().getList().size(); i++) {
                        if (bean.getData().getList().get(i).getIf_moren().equals("1")) {
                            defaultPosition = i;//获取默认地址所在实体类里的集合位置
                            break;
                        }
                    }
                    //设置显示以保存的地址

                    addressee = bean.getData().getList().get(defaultPosition).getUsername();
                    addressId = bean.getData().getList().get(defaultPosition).getAddr_id();
                    addressPhone = bean.getData().getList().get(defaultPosition).getCellphone();
                    addressInfo = bean.getData().getList().get(defaultPosition).getAddr_info();
                    nameTv.setText("收件人: " + addressee);
                    numberTv.setText("电话: " + addressPhone);
                    numberTv.setVisibility(View.VISIBLE);
                    addressTv.setText("地址: " + addressInfo);
                    addressTv.setVisibility(View.VISIBLE);
                    writeAddressTv.setText(R.string.activity_buyDetails_writeAddressTV_text_changeAddress);

                }
            }
        });
    }

    @Override
    public void requestFailed(String cause) {

    }
}
