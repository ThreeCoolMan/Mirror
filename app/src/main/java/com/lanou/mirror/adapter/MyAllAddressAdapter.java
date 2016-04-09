package com.lanou.mirror.adapter;

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
