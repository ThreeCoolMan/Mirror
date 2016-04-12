package com.lanou.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.bean.MyAllAddressBeans;
import com.lanou.mirror.listener.OnSlideListener;
import com.lanou.mirror.tools.SwipeListView;
import com.lanou.mirror.tools.SwipeView;

import java.util.ArrayList;

/**
 * Created by dllo on 16/4/8.
 */
public class MyAllAddressAdapter extends BaseAdapter {
    private ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data;
    private Context context;
    private SwipeView mOldSwipView;

    public MyAllAddressAdapter(ArrayList<MyAllAddressBeans.DataEntity.ListEntity> data, Context context) {
        super();
        this.data = data;
        this.context = context;
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
        MyViewHolder holder ;
        SwipeView swipeView = (SwipeView) convertView;
        if (swipeView == null) {
            View view = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_listview_activity_myaddress, null);
            swipeView = new SwipeView(context);
            swipeView.setContentItemView(view);
            holder = new MyViewHolder(swipeView);
           swipeView.setOnSlideListener(new OnSlideListener() {
               @Override
               public void onSlide(View view, int status) {
                   if (mOldSwipView !=null&& mOldSwipView !=view){
                       mOldSwipView.shrink();
                   }
               }
           });
            swipeView.setTag(holder);

        } else {
            holder = (MyViewHolder) swipeView.getTag();
        }
        if (SwipeListView.mSwipeView!=null){
            SwipeListView.mSwipeView.shrink();
            MyAllAddressBeans.DataEntity.ListEntity myAllAddressBeans = (MyAllAddressBeans.DataEntity.ListEntity) getItem(position);
            if (myAllAddressBeans==null){
                return  swipeView;

            }

        }

        holder.numberTv.setText("电话 ："+data.get(position).getCellphone());
        holder.addressTv.setText("地址: " + data.get(position).getAddr_info());
        holder.receiveTv.setText("收件人: " + data.get(position).getUsername());
        holder.leftView.setText("删除");
        holder.leftView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data==null){
                    return;
                }
                data.remove(position);
                notifyDataSetChanged();
            }
        });
        return swipeView;
    }

    class MyViewHolder {
        private TextView receiveTv, addressTv, numberTv;
        private ImageView editIv;
        private TextView leftView;
        MyViewHolder(View view){
          receiveTv = (TextView) view.findViewById(R.id.item_listview_activity_myaddress_receiveman);
            addressTv = (TextView) view.findViewById(R.id.item_listview_activity_myaddress_address);
            numberTv = (TextView) view.findViewById(R.id.item_listview_activity_myaddress_number);
            editIv = (ImageView) view.findViewById(R.id.item_listview_activity_myaddress_edit);
            leftView = (TextView) view.findViewById(R.id.tv_left);

        }
        
    }
}
//
//import android.content.Context;
//import android.view.View;
//import android.widget.TextView;
//
//import com.lanou.mirror.R;
//import com.lanou.mirror.base.BaseListViewAdapter;
//import com.lanou.mirror.bean.MyAllAddressBeans;
//
//import java.util.List;
//
///**
// * Created by Yi on 16/4/9.
// */
//public class MyAllAddressAdapter extends BaseListViewAdapter<MyAllAddressBeans.DataEntity.ListEntity> {
//
//
//    public MyAllAddressAdapter(List<MyAllAddressBeans.DataEntity.ListEntity> data, Context context) {
//        super(data, context);
//    }
//
//    @Override
//    protected View getItemVIew(int position, ViewHolder holder, View convertView) {
//        TextView addressee = holder.getView(R.id.item_myAllAddress_listView_tv_addressee);
//        TextView address = holder.getView(R.id.item_myAllAddress_listView_tv_address);
//        TextView number = holder.getView(R.id.item_myAllAddress_listView_tv_number);
//        addressee.setText("收件人: " + data.get(position).getUsername());
//        address.setText("地址: " + data.get(position).getAddr_info());
//        number.setText("电话: " + data.get(position).getCellphone());
//        return convertView;
//    }
//
//    @Override
//    protected int getItemResource() {
//        return R.layout.item_myalladdress_listview;
//
//    }
//}
