package com.lanou.mirror.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.ProductDetailsItemListioner;
import com.lanou.mirror.R;
import com.lanou.mirror.adapter.ProductDetailsAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.HashMap;


/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<GoodsListBeans>, ProductDetailsItemListioner, View.OnClickListener {
    private RecyclerView productDetailsRv;
    private ProductDetailsAdapter detailsAdapter;
    private GoodsListBeans goodsListBeans;
    private View newFl;
    private View newIv;
    private PhotoView deailsPv;
    private ImageButton ibBack, ibShopping;
    Info mInfo;
    AlphaAnimation in = new AlphaAnimation(0, 1);
    AlphaAnimation out = new AlphaAnimation(1, 0);

    @Override
    protected int setContent() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initView() {
        in.setDuration(300);
        out.setDuration(300);
        ibBack = bindView(R.id.activity_peoduct_back_btn);
        ibShopping = bindView(R.id.activity_peoduct_shoping_btn);
        newFl = bindView(R.id.activity_product_newfl);
        newIv = bindView(R.id.activity_peoduct_newiv);
        deailsPv = bindView(R.id.activity_peoduct_newpv);
        productDetailsRv = bindView(R.id.activity_peoduct_details_rv);
        newFl.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        ibShopping.setOnClickListener(this);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        productDetailsRv.setLayoutManager(gm);
    }

    @Override
    protected void initData() {
       getData();

    }
    public void getData(){
        HashMap<String, String> params = new HashMap<>();
        params.put("token", "");
        params.put("device_type", "3");
        params.put("page", "");
        params.put("last_time", "");
        params.put("category_id", "");
        params.put("version", "1.0.1");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(PRODUCTS_GOODS_LIST_URL, params, GoodsListBeans.class, this);
    }

    //这里是点击小图变大图
    @Override
    public void productDetailsItemListioner(int position, View v,GoodsListBeans beans) {
        mInfo = ((PhotoView) v).getInfo();
        //每次根据接口中的beans参数获得相对应的图片
        OkHttpNetHelper.getOkHttpNetHelper().setOkImage
                (beans.getData().getList().get(0).getWear_video().get(position+1).getData(), deailsPv);
        newIv.startAnimation(in);//背景渐变到黑色
        newFl.setVisibility(View.VISIBLE);
        newFl.startAnimation(in);
        deailsPv.animaFrom(mInfo);
    }

    @Override
    public void videoViewListioner() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //这里是点击大图变回原来位置
            case R.id.activity_product_newfl:
                newIv.startAnimation(out);
                deailsPv.animaTo(mInfo, new Runnable() {
                    @Override
                    public void run() {
                        newFl.setVisibility(View.GONE);
                    }
                });
                break;
            case R.id.activity_peoduct_back_btn:
                this.finish();
                break;
            case R.id.activity_peoduct_shoping_btn:
                break;
        }
    }

    @Override
    public void requestSucceed(String result, final GoodsListBeans bean) {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                detailsAdapter = new ProductDetailsAdapter(getApplication(), bean);
                productDetailsRv.setAdapter(detailsAdapter);
                detailsAdapter.SetDetailsListener(ProductDetailsActivity.this);
            }
        });
    }

    @Override
    public void requestFailed(String cause) {

    }
}
