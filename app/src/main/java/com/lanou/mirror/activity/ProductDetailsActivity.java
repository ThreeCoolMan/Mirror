package com.lanou.mirror.activity;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.lanou.mirror.Interface.ProductDetailsItemListioner;
import com.lanou.mirror.R;
import com.lanou.mirror.adapter.ProductDetailsAdapter;
import com.lanou.mirror.base.BaseActivity;

import java.util.zip.Inflater;

/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsActivity extends BaseActivity implements ProductDetailsItemListioner{
    private RecyclerView productDetailsRv;
    private ProductDetailsAdapter detailsAdapter;

    @Override
    protected int setContent() {
        return R.layout.activity_product_details;
    }

    @Override
    protected void initView() {

        detailsAdapter = new ProductDetailsAdapter();
        productDetailsRv = bindView(R.id.activity_peoduct_details_rv);
        detailsAdapter.SetDetailsListener(this);
        productDetailsRv.setAdapter(detailsAdapter);
        GridLayoutManager gm = new GridLayoutManager(this, 1);
        productDetailsRv.setLayoutManager(gm);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void productDetailsItemListioner(int position) {
        Log.d("android","position"+position);
        Toast.makeText(ProductDetailsActivity.this, position+"", Toast.LENGTH_SHORT).show();
    }
}
