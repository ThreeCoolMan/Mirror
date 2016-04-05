package com.lanou.mirror.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.bean.GoodsListBean;
import com.lanou.mirror.tools.OkHttpNetHelper;

/**
 * Created by 何伟东 on 16/3/29.
 */
public class AllBrowsingFragmentAdapter extends RecyclerView.Adapter<AllBrowsingFragmentAdapter.MyViewHolder> {
    private GoodsListBean data;
    private int pos;
    private OkHttpNetHelper helper;


    public AllBrowsingFragmentAdapter(GoodsListBean data, int pos) {
        this.data = data;
        this.pos = pos;
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
            helper.setOkImage(data.getData().getList().get(pos).getGoods_img(), holder.iv);
            holder.cityTv.setText(data.getData().getList().get(pos).getProduct_area());
            holder.descriptionTv.setText(data.getData().getList().get(pos).getBrand());
            holder.nameTv.setText(data.getData().getList().get(pos).getGoods_name());
            holder.priceTv.setText(data.getData().getList().get(pos).getGoods_price());
        } else {
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
