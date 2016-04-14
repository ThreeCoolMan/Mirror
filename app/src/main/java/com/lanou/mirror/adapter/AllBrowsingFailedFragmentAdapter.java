package com.lanou.mirror.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.greendao.Cache;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/4/8.
 */
public class AllBrowsingFailedFragmentAdapter extends RecyclerView.Adapter<AllBrowsingFailedFragmentAdapter.MyViewHolder> {
    private List<Cache> data;
    private int pos;

    public AllBrowsingFailedFragmentAdapter(ArrayList<Cache> data, int pos) {
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
            OkHttpNetHelper.getOkHttpNetHelper().setOkImage(data.get(pos).getUrl(),holder.iv);
            holder.cityTv.setText(data.get(pos).getCity());
            holder.descriptionTv.setText(data.get(pos).getDescription());
            holder.nameTv.setText(data.get(pos).getBrand());
            holder.priceTv.setText(data.get(pos).getPrice());
            holder.shareRelativeLayout.setVisibility(View.GONE);
        } else if (pos == 3) {
            OkHttpNetHelper.getOkHttpNetHelper().setOkImage(data.get(pos).getUrl(), holder.iv);
            holder.shareDescriptionTv.setText(data.get(pos).getTitle());
            holder.shareRelativeLayout.setVisibility(View.VISIBLE);
            holder.layout.setVisibility(View.GONE);
        }
        else {
            holder.layout.setVisibility(View.GONE);
            holder.shareRelativeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private RelativeLayout layout;
        private TextView priceTv, nameTv, cityTv, descriptionTv, shareDescriptionTv;
        private RelativeLayout shareRelativeLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_fragmentallbrowsing_iv);
            nameTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_name_tv);
            priceTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_price_tv);
            cityTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_city_tv);
            descriptionTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_descripsion_tv);
            layout = (RelativeLayout) itemView.findViewById(R.id.item_fragmentallbrowsing_relativelayout);
            shareDescriptionTv = (TextView) itemView.findViewById(R.id.item_fragmentallbrowsing_share_descripsion_tv);
            shareRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.fragment_allbrowsing_share_relativelayout);
        }
    }
}
