package com.lanou.mirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.bm.library.PhotoView;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.listener.ProductDetailsItemListioner;
import com.lanou.mirror.R;
import com.lanou.mirror.tools.OkHttpNetHelper;

/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private GoodsListBeans goodsListBeans;
    private ProductDetailsItemListioner listioner;
    public ProductDetailsAdapter(Context context, GoodsListBeans goodsListBeans) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
        notifyDataSetChanged();;
    }

    public void SetDetailsListener(ProductDetailsItemListioner listioner) {
        this.listioner = listioner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 0) {
            return new ProductDetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_recycleview, parent, false));
        }
        return new HeadProductDetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_recycleview_header, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProductDetailsHolder) {
            OkHttpNetHelper.getOkHttpNetHelper().setOkImage(goodsListBeans.getData().getList().get(0).getWear_video().get(position + 1).getData(),
                    ((ProductDetailsHolder) holder).detailsIv);

        } else if (holder instanceof HeadProductDetailsHolder) {
            ((HeadProductDetailsHolder) holder).detailsVv.getId();
       }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return goodsListBeans.getData().getList().get(0).getWear_video().size() - 1;

    }

    class ProductDetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        private LinearLayout linearLayout;
        private PhotoView detailsIv;

        public ProductDetailsHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_details_ll_details);
            detailsIv = (PhotoView) itemView.findViewById(R.id.item_details_iv_details);
            detailsIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listioner != null) {
                listioner.productDetailsItemListioner(getItemViewType(), v, goodsListBeans);

            }
        }
    }

    class HeadProductDetailsHolder extends RecyclerView.ViewHolder {
        private int position;
        private VideoView detailsVv;

        public HeadProductDetailsHolder(View itemView) {
            super(itemView);
            detailsVv = (VideoView) itemView.findViewById(R.id.item_details_vv_details);
        }
    }


}
