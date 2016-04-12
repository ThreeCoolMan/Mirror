package com.lanou.mirror.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bm.library.PhotoView;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.listener.ProductDetailsItemListener;
import com.lanou.mirror.R;
import com.lanou.mirror.tools.OkHttpNetHelper;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private GoodsListBeans goodsListBeans;
    private ProductDetailsItemListener listener;
    private int pos;
    private String url;

    public ProductDetailsAdapter(Context context, GoodsListBeans goodsListBeans, int pos) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
        this.pos = pos;
        notifyDataSetChanged();
    }

    public void SetDetailsListener(ProductDetailsItemListener listener) {
        this.listener = listener;
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
            if (pos == 0) {
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(goodsListBeans.getData().getList().get(pos).getWear_video().get(position + 1).getData(),
                        ((ProductDetailsHolder) holder).detailsIv);


            } else if (pos == 2) {
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(goodsListBeans.getData().getList().get(pos).getWear_video().get(position).getData(),
                        ((ProductDetailsHolder) holder).detailsIv);
            }
        } else if (holder instanceof HeadProductDetailsHolder) {
            if (pos == 0) {
                url = goodsListBeans.getData().getList().get(pos).getWear_video().get(0).getData();
                ((HeadProductDetailsHolder) holder).detailsVv.setUp(url, null);
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(goodsListBeans.getData().getList().get(pos).getWear_video().get(position + 1).getData(),
                        ((HeadProductDetailsHolder) holder).detailsVv.ivThumb);
            } else if (pos == 2) {
                url = goodsListBeans.getData().getList().get(pos).getWear_video().get(4).getData();
                ((HeadProductDetailsHolder) holder).detailsVv.setUp(url, null);
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(goodsListBeans.getData().getList().get(pos).getWear_video().get(position).getData(),
                        ((HeadProductDetailsHolder) holder).detailsVv.ivThumb);
            }
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return goodsListBeans.getData().getList().get(pos).getWear_video().size() - 1;
    }

    class ProductDetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


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
            if (listener != null) {
                listener.productDetailsItemListener(getItemViewType(), v, goodsListBeans);

            }
        }
    }

    class HeadProductDetailsHolder extends RecyclerView.ViewHolder {

        private JCVideoPlayer detailsVv;

        public HeadProductDetailsHolder(View itemView) {
            super(itemView);
            detailsVv = (JCVideoPlayer) itemView.findViewById(R.id.item_details_vv_details);
        }
    }
}
