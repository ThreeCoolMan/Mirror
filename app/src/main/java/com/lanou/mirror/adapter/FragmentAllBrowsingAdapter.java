package com.lanou.mirror.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanou.mirror.R;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class FragmentAllBrowsingAdapter extends RecyclerView.Adapter<FragmentAllBrowsingAdapter.MyViewHolder> {
    private ArrayList<String> data;

    public FragmentAllBrowsingAdapter(ArrayList<String> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,null);
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
        private TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_tv);
        }
    }
}
