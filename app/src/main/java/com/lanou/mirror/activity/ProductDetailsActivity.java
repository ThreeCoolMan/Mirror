package com.lanou.mirror.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.lanou.mirror.listener.ProductDetailsItemListioner;
import com.lanou.mirror.R;
import com.lanou.mirror.adapter.ProductDetailsAdapter;
import com.lanou.mirror.base.BaseActivity;


/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsActivity extends BaseActivity implements ProductDetailsItemListioner, View.OnClickListener {
    private RecyclerView productDetailsRv;
    private ProductDetailsAdapter detailsAdapter;
    private int[] imgs = {R.mipmap.iv_money, R.mipmap.ic_launcher, R.mipmap.iv_show_frame, R.mipmap.iv_blog_icon, R.mipmap.iv_display_page};
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
        detailsAdapter = new ProductDetailsAdapter(this, imgs);
        ibBack = bindView(R.id.activity_peoduct_back_btn);
        ibShopping = bindView(R.id.activity_peoduct_shoping_btn);
        newFl = bindView(R.id.activity_product_newfl);
        newIv = bindView(R.id.activity_peoduct_newiv);
        deailsPv = bindView(R.id.activity_peoduct_newpv);
        productDetailsRv = bindView(R.id.activity_peoduct_details_rv);
        newFl.setOnClickListener(this);
        ibBack.setOnClickListener(this);
        ibShopping.setOnClickListener(this);
        detailsAdapter.SetDetailsListener(this);
        productDetailsRv.setAdapter(detailsAdapter);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        productDetailsRv.setLayoutManager(gm);

    }

    @Override
    protected void initData() {

    }

    //这里是点击小图变大图
    @Override
    public void productDetailsItemListioner(int position, View v) {

        mInfo = ((PhotoView) v).getInfo();
        deailsPv.setImageResource(imgs[position]);
        newIv.startAnimation(in);//背景渐变到黑色
        newFl.setVisibility(View.VISIBLE);
        newFl.startAnimation(in);
        deailsPv.animaFrom(mInfo);
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
                break;
            case R.id.activity_peoduct_shoping_btn:
                break;
        }
    }

}
