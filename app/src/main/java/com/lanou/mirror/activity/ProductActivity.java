package com.lanou.mirror.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.ProductBottomListViewAdapter;
import com.lanou.mirror.adapter.ProductTopListviewAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.LinkageListView;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;


/**
 * Created by dllo on 16/4/1.
 */
public class ProductActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<GoodsListBeans>, View.OnClickListener {
    private LinkageListView mListView;
    private ProductBottomListViewAdapter bottomAdapter;
    private ProductTopListviewAdapter topAdapter;
    private ImageView backGroundIv, closeIv;
    private int position;
    private RelativeLayout relativeLayout;
    private TextView atlasTv;
    private ImageButton buyImageViewButton;
    private String token = "";
    private GoodsListBeans passBeans;

    @Override
    protected int setContent() {
        return R.layout.activity_product;

    }

    @Override
    protected void initView() {
        mListView = bindView(R.id.activity_prodoct_llv);
        backGroundIv = bindView(R.id.activity_product_iv_background);
        relativeLayout = bindView(R.id.activity_prodoct_rl);
        closeIv = bindView(R.id.activity_prodoct_btn_comeback);
        closeIv.setOnClickListener(this);
        atlasTv = bindView(R.id.activity_product_tv_atlas);
        atlasTv.setOnClickListener(this);
        buyImageViewButton = bindView(R.id.activity_product_imageButton_buy);
        buyImageViewButton.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        token = intent.getStringExtra("token");

        HashMap<String, String> params = new HashMap<>();
        params.put("token", "");
        params.put("device_type", "3");
        params.put("page", "");
        params.put("last_time", "");
        params.put("category_id", "");
        params.put("version", "");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(PRODUCTS_GOODS_LIST_URL, params, GoodsListBeans.class, this);

        mListView.setLinkListViewListener(relativeLayout);//对表层 listView 进行监听
    }

    @Override
    public void requestSucceed(String result, final GoodsListBeans goodsListBeans) {

        passBeans = goodsListBeans;
        runOnUiThread(new Runnable() {//主线程刷新ui
            @Override
            public void run() {
                String url = goodsListBeans.getData().getList().get(position).getGoods_img();
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, backGroundIv);

                //自定义组件中的方法 只需要添加两个adapter参数即可
                bottomAdapter = new ProductBottomListViewAdapter(getApplication(), goodsListBeans, position);
                topAdapter = new ProductTopListviewAdapter(getApplication(), goodsListBeans, position);
                mListView.setAdapter(bottomAdapter, topAdapter);
                mListView.setLinkageSpeed(1.2f);//设置当前listView的滑动速度,封装好的方法
            }
        });

    }

    @Override
    public void requestFailed(String cause) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_prodoct_btn_comeback:
                finish();
                break;
            case R.id.activity_product_tv_atlas:

                Intent intent = new Intent(ProductActivity.this, ProductDetailsActivity.class);
                startActivity(intent);
                break;

            case R.id.activity_product_imageButton_buy:

                if (token == null) {
                    Intent intentLogin = new Intent(ProductActivity.this, LoginActivity.class);
                    startActivity(intentLogin);
                } else if (passBeans != null) {
                    String goodsId = passBeans.getData().getList().get(position).getGoods_id();
                    Intent intentBuy = new Intent(ProductActivity.this, BuyDetailsActivity.class);
                    intentBuy.putExtra("token", token);
                    intentBuy.putExtra("goodsId", goodsId);
                    startActivity(intentBuy);
                }

                break;

        }
    }
}
