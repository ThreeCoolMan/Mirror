package com.lanou.mirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.activity.ProductActivity;
import com.lanou.mirror.activity.TopicsShareActivity;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.bean.TopicsShareBeans;
import com.lanou.mirror.tools.OkHttpNetHelper;

/**
 * Created by 何伟东 on 16/3/29.
 */
public class AllBrowsingFragmentAdapter extends RecyclerView.Adapter<AllBrowsingFragmentAdapter.MyViewHolder> {
    private GoodsListBeans goodData;
    private TopicsShareBeans shareData;
    private int pos;
    private OkHttpNetHelper helper;
    private Context context;


    public<T> AllBrowsingFragmentAdapter(T t, int pos ,Context context) {
        this.pos = pos;
        this.context = context;
        if (pos==3){
            this.shareData = (TopicsShareBeans) t;
        }
        else {
            this.goodData = (GoodsListBeans) t;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_fragmentallbrowsing, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (pos < 3) {
            helper =OkHttpNetHelper.getOkHttpNetHelper();
            helper.setOkImage(goodData.getData().getList().get(pos).getGoods_img(), holder.iv);
            holder.cityTv.setText(goodData.getData().getList().get(pos).getProduct_area());
            holder.descriptionTv.setText(goodData.getData().getList().get(pos).getBrand());
            holder.nameTv.setText(goodData.getData().getList().get(pos).getGoods_name());
            holder.priceTv.setText(goodData.getData().getList().get(pos).getGoods_price());
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductActivity.class);
                    intent.putExtra("position",pos);
                    intent.putExtra("id",goodData.getData().getList().get(pos).getGoods_id());
                    context.startActivity(intent);
                }
            });
        }else if (pos == 3){
            helper =OkHttpNetHelper.getOkHttpNetHelper();
            helper.setOkImage(shareData.getData().getList().get(1).getStory_img(),holder.iv);
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TopicsShareActivity.class);

                    context.startActivity(intent);
                }
            });
            holder.layout.setVisibility(View.GONE);
        }
        else {
            holder.layout.setVisibility(View.GONE);
        }

    }


    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private RelativeLayout layout;
        private TextView priceTv, nameTv, cityTv, descriptionTv;


        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_fragmentallbrowsing_iv);
            nameTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_name_tv);
            priceTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_price_tv);
            cityTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_city_tv);
            descriptionTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_descripsion_tv);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_fragmentallbrowsing_relativelayout);

        }
    }
}
