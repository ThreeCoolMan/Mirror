package com.lanou.mirror.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseApplication;

/**
 * Created by dllo on 16/4/8.
 */
public class MyAllAddressAdapter extends BaseAdapter {
    public MyAllAddressAdapter() {
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_listview_activity_myaddress, null);
            holder = new MyViewHolder();
            holder.receiveTv = (TextView) convertView.findViewById(R.id.item_listview_activity_myaddress_receiveman);
            holder.addressTv = (TextView) convertView.findViewById(R.id.item_listview_activity_myaddress_address);
            holder.numberTv = (TextView) convertView.findViewById(R.id.item_listview_activity_myaddress_number);
            holder.editIv = (ImageView) convertView.findViewById(R.id.item_listview_activity_myaddress_edit);
            convertView.setTag(holder);

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.numberTv.setText("110");
        holder.addressTv.setText("北京");
        holder.receiveTv.setText("马哥");
        return convertView;
    }

    class MyViewHolder {
        private TextView receiveTv, addressTv, numberTv;
        private ImageView editIv;

    }
}
