package com.lanou.mirror.adapter;

//<<<<<<< HEAD
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.lanou.mirror.R;
//import com.lanou.mirror.base.BaseApplication;
//
///**
// * Created by dllo on 16/4/8.
// */
//public class MyAllAddressAdapter extends BaseAdapter {
//    public MyAllAddressAdapter() {
//    }
//
//    @Override
//    public int getCount() {
//        return 10;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return position;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        MyViewHolder holder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_listview_activity_myaddress, null);
//            holder = new MyViewHolder();
//            holder.receiveTv = (TextView) convertView.findViewById(R.id.item_listview_activity_myaddress_receiveman);
//            holder.addressTv = (TextView) convertView.findViewById(R.id.item_listview_activity_myaddress_address);
//            holder.numberTv = (TextView) convertView.findViewById(R.id.item_listview_activity_myaddress_number);
//            holder.editIv = (ImageView) convertView.findViewById(R.id.item_listview_activity_myaddress_edit);
//            convertView.setTag(holder);
//
//        } else {
//            holder = (MyViewHolder) convertView.getTag();
//        }
//        holder.numberTv.setText("110");
//        holder.addressTv.setText("北京");
//        holder.receiveTv.setText("马哥");
//        return convertView;
//    }
//
//    class MyViewHolder {
//        private TextView receiveTv, addressTv, numberTv;
//        private ImageView editIv;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseListViewAdapter;
import com.lanou.mirror.bean.MyAllAddressBeans;

import java.util.List;

/**
 * Created by Yi on 16/4/9.
 */
public class MyAllAddressAdapter extends BaseListViewAdapter<MyAllAddressBeans.DataEntity.ListEntity> {


    public MyAllAddressAdapter(List<MyAllAddressBeans.DataEntity.ListEntity> data, Context context) {
        super(data, context);
    }

    @Override
    protected View getItemVIew(int position, ViewHolder holder, View convertView) {
        TextView addressee = holder.getView(R.id.item_myAllAddress_listView_tv_addressee);
        TextView address = holder.getView(R.id.item_myAllAddress_listView_tv_address);
        TextView number = holder.getView(R.id.item_myAllAddress_listView_tv_number);
        addressee.setText("收件人: " + data.get(position).getUsername());
        address.setText("地址: " + data.get(position).getAddr_info());
        number.setText("电话: " + data.get(position).getCellphone());
        return convertView;
    }

    @Override
    protected int getItemResource() {
        return R.layout.item_myalladdress_listview;

    }
}
