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
import com.lanou.mirror.listener.ProductDetailsItemListioner;
import com.lanou.mirror.R;

/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ProductDetailsItemListioner listioner;
    private int[] imgs;

    public ProductDetailsAdapter(Context context, int[] imgs) {

        this.imgs = imgs;
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
            ((ProductDetailsHolder) holder).detailsIv.setImageResource(imgs[position]);
        } else if (holder instanceof HeadProductDetailsHolder) {
            ((HeadProductDetailsHolder) holder).detailsVv.getId();

//            MediaController mediaController = new MediaController(context);
//            //调整播放进度条的位置
//            mediaController.setPadding(36,45,33,2);
//            //用这句话可以播放
//            mediaController.setAnchorView(((HeadProductDetailsHolder) holder).detailsVv);
//            ((HeadProductDetailsHolder) holder).detailsVv.setMediaController(mediaController);
//            ((HeadProductDetailsHolder) holder).detailsVv.requestFocus();
        }
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imgs.length;
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
                listioner.productDetailsItemListioner(getItemViewType(), v);
                Log.d("android", getItemViewType() + "");
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
