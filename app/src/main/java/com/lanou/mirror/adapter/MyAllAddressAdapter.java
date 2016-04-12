package com.lanou.mirror.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.bean.MyAllAddressBeans;
import com.lanou.mirror.listener.OnSlideListener;

import java.security.Principal;
import java.util.ArrayList;

/**
 * Created by dllo on 16/4/8.
 */
public class MyAllAddressAdapter extends BaseAdapter {
    private ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data;
    private Context context;
    private onRightItemClickListener mListener;
    private int mRigthWidth = 0;


    public MyAllAddressAdapter(ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data, Context context, int mRigthWidth) {
        super();
        this.data = data;
        this.mRigthWidth = mRigthWidth;
        this.context = context;
    }

    public void setData(ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data) {
        this.data = data;
    }

//    public void setListData(ArrayList<MyAllAddressBeans.DataEntity.ListEntity> lists) {
//        data =  lists;
//        notifyDataSetChanged();
//    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_listview_activity_myaddress, null);

            holder = new MyViewHolder(convertView);

            convertView.setTag(holder);

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.position = position;
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(mRigthWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        holder.itemRight.setLayoutParams(lp1);


        holder.numberTv.setText("电话 ：" + data.get(position).getCellphone());
        holder.addressTv.setText("地址: " + data.get(position).getAddr_info());
        holder.receiveTv.setText("收件人: " + data.get(position).getUsername());


        return convertView;
    }

    class MyViewHolder {
        private TextView receiveTv, addressTv, numberTv, rigthTv;
        private ImageView editIv;
        private RelativeLayout itemRight;
        private int position;

        MyViewHolder(View view) {
            itemRight = (RelativeLayout) view.findViewById(R.id.item_right);
            receiveTv = (TextView) view.findViewById(R.id.item_listview_activity_myaddress_receiveman);
            addressTv = (TextView) view.findViewById(R.id.item_listview_activity_myaddress_address);
            numberTv = (TextView) view.findViewById(R.id.item_listview_activity_myaddress_number);
            editIv = (ImageView) view.findViewById(R.id.item_listview_activity_myaddress_edit);
            rigthTv = (TextView) view.findViewById(R.id.item_right_txt);
            itemRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d("click", v.toString());
                    if (mListener != null) {

                        mListener.onRightItemClick(v, position);
                    }
                }
            });

        }

    }

    public void setOnRightItemClickListener(onRightItemClickListener listener) {
        mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}

