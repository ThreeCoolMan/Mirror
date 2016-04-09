package com.lanou.mirror.activity;

import android.content.Intent;
import android.widget.ImageView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.ProductBottomListviewAdapter;
import com.lanou.mirror.adapter.ProductTopListviewAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.GoodsDetailsBean;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.LinkageListView;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;


/**
 * Created by dllo on 16/4/1.
 */
public class ProductActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<GoodsDetailsBean> {
    private LinkageListView mlistView;
    private ProductBottomListviewAdapter bottomAdapter;
    private ImageView backGroundIv;
    private int position;

    @Override
    protected int setContent() {
        return R.layout.activity_product;
    }

    @Override
    protected void initView() {
        mlistView = bindView(R.id.activity_prodoct_llv);
        backGroundIv = bindView(R.id.activity_product_iv_background);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        String id = intent.getStringExtra("id");
        HashMap<String, String> params = new HashMap<>();
        params.put("token", "");
        params.put("device_type", "3");
        params.put("version", "1.0.1");
        params.put("goods_id", id);

        OkHttpNetHelper.getOkHttpNetHelper().postRequest(PRODUCTS_GOODS_INFO_URL, params, GoodsDetailsBean.class, this);
    }


    @Override
    public void requestSucceed(String result, final GoodsDetailsBean bean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String url = bean.getData().getGoods_img();
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, backGroundIv);

                bottomAdapter = new ProductBottomListviewAdapter(getApplication(), bean, position);
                mlistView.setAdapter(bottomAdapter, new ProductTopListviewAdapter(getApplication(), bean, position));
                mlistView.setLinkageSpeed(1.2f);//设置当前listview的滑动速度,封装好的方法
            }
        });
    }

    @Override
    public void requestFailed(String cause) {

    }

}
