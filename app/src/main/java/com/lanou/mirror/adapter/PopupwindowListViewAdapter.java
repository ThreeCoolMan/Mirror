package com.lanou.mirror.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.activity.MainActivity;

/**
 * Created by 何伟东 on 16/3/30.
 */
public class PopupwindowListViewAdapter extends BaseAdapter {
    String [] titles = {"浏览所有分类","浏览平光眼镜","浏览太阳眼镜","专题分享","我的购物车","返回首页","退出"};
    private Context context;

    public PopupwindowListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 7;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder();
        if (convertView==null){

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_fragment_popupwindow,null);
            holder.tv  = (TextView) convertView.findViewById(R.id.item_fragment_popupwindow_listview_tv);
            holder.iv = (ImageView) convertView.findViewById(R.id.item_fragment_popupwindow_listview_iv);
            convertView.setTag(holder);
        }
        else {
            holder = (MyViewHolder) convertView.getTag();
        }
        holder.tv.setText(titles[position]);
        holder.iv.setVisibility(View.GONE);
        holder.tv.setOnClickListener(new ItemListen(position));


        return convertView;
    }
    class MyViewHolder {
        private TextView tv;
        private ImageView iv;
    }
    class ItemListen implements View.OnClickListener {
        private int position;
        public ItemListen(int pos) {
            position = pos;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,MainActivity.class);
            intent.putExtra("list",position);
            context.startActivity(intent);

        }
    }
}
