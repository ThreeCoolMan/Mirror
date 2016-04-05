package com.lanou.mirror.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanou.mirror.R;

import java.util.ArrayList;

/**
 * Created by 何伟东 on 16/3/29.
 */
public class AllBrowsingFragmentAdapter extends RecyclerView.Adapter<AllBrowsingFragmentAdapter.MyViewHolder> {
    private ArrayList<String> data;

    public AllBrowsingFragmentAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_fragmentallbrowsing, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
