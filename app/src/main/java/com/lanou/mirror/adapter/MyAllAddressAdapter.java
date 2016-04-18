package com.lanou.mirror.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.lanou.mirror.activity.ModifyAddressActivity;
import com.lanou.mirror.activity.MyAllAddressActivity;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.bean.MyAllAddressBeans;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/8.
 */
public class MyAllAddressAdapter extends BaseAdapter {
    private ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data;
    private Context context;
    private onRightItemClickListener mListener;
    private int mRigthWidth = 0;
    private String token, goodsId;


    public MyAllAddressAdapter(ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data, Context context, int mRigthWidth, String token, String goodsId) {
        super();
        this.data = data;
        this.mRigthWidth = mRigthWidth;
        this.context = context;
        this.token = token;
        this.goodsId = goodsId;
    }

    public void setData(ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data) {
        this.data = data;
    }

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

        Log.d("position", String.valueOf(position));
        holder.numberTv.setText("电话 ：" + data.get(position).getCellphone());
        holder.addressTv.setText("地址: " + data.get(position).getAddr_info());
        holder.receiveTv.setText("收件人: " + data.get(position).getUsername());
        holder.editIv.setOnClickListener(new View.OnClickListener() { //编辑监听，跳转编辑页面
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ModifyAddressActivity.class);
                intent.putExtra("token", token);
                intent.putExtra("addressee", data.get(position).getUsername());
                intent.putExtra("address", data.get(position).getAddr_info());
                intent.putExtra("phoneNumber", data.get(position).getCellphone());
                intent.putExtra("addressId", data.get(position).getAddr_id());
                intent.putExtra("goodsId", goodsId);
                ((MyAllAddressActivity)context).startActivityForResult(intent,666);

            }


        });
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

