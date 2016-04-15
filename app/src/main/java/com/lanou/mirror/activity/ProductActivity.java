package com.lanou.mirror.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
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
    //自定义可实现视差滑动的View.
    private LinkageListView mListView;
    //自定义View需要两个adapter
    private ProductBottomListViewAdapter bottomAdapter;
    private ProductTopListviewAdapter topAdapter;
    private ImageView backGroundIv, closeIv;
    private int position;
    private RelativeLayout relativeLayout;
    private TextView atlasTv;
    private ImageButton buyImageViewButton;
    private String token = "";
    private GoodsListBeans passBeans;
    private FrameLayout frameLayout = null;
    private int changeY;

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
        params.put("version", "1.0.1");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(PRODUCTS_GOODS_LIST_URL, params, GoodsListBeans.class, this);
        mListView.setLinkListViewListener(relativeLayout);//对表层 listView 进行监听

    }

    @Override
    public void requestSucceed(String result, final GoodsListBeans goodsListBeans) {
        passBeans = goodsListBeans;
        runOnUiThread(new Runnable() {
            private String url;

            @Override
            public void run() {
                url = goodsListBeans.getData().getList().get(position).getGoods_img();
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, backGroundIv);
                //自定义组件中的方法 只需要添加两个adapter参数即可
                bottomAdapter = new ProductBottomListViewAdapter(getApplication(), goodsListBeans, position);
                topAdapter = new ProductTopListviewAdapter(getApplication(), goodsListBeans, position);
                mListView.setAdapter(bottomAdapter, topAdapter);
                mListView.setLinkageSpeed(1.2f);//设置当前listView的滑动速度

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
                intent.putExtra("position", position);
                intent.putExtra("token", token);
                startActivity(intent);
                break;
            case R.id.activity_product_imageButton_buy:
                if (token == null) {
                    Intent intentLogin = new Intent(ProductActivity.this, LoginActivity.class);
                    intentLogin.putExtra("jumpFromMain", false);//boolean 值用于判断登录后跳转那个页面
                    startActivityForResult(intentLogin, 999);
                } else if (passBeans != null) {
                    String goodsId = passBeans.getData().getList().get(position).getGoods_id();
                    String price = passBeans.getData().getList().get(position).getGoods_price();
                    Intent intentBuy = new Intent(ProductActivity.this, BuyDetailsActivity.class);
                    intentBuy.putExtra("token", token);
                    intentBuy.putExtra("goodsId", goodsId);
                    intentBuy.putExtra("price", price);
                    startActivity(intentBuy);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 888:
                token = data.getStringExtra("token");
                break;
        }
    }

}
