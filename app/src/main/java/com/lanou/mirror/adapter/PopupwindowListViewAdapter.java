package com.lanou.mirror.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.activity.MainActivity;
import com.lanou.mirror.base.BaseApplication;

/**
 * Created by 何伟东 on 16/3/30.
 */
public class PopupwindowListViewAdapter extends BaseAdapter {
    private Context context;
    private int pos;
    String [] titles = BaseApplication.getContext().getResources().getStringArray(R.array.titles);

    public PopupwindowListViewAdapter(Context context,int pos) {
        this.context = context;
        this.pos = pos;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_fragment_popupwindow, null);
            holder.tv = (TextView) convertView.findViewById(R.id.item_fragment_popupwindow_listview_tv);
            holder.iv = (ImageView) convertView.findViewById(R.id.item_fragment_popupwindow_listview_iv);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.item_listview_fragment_linearlayout);

            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        holder.tv.setText(titles[position]);
        holder.iv.setVisibility(View.GONE);
        if (pos==position){
            holder.iv.setVisibility(View.VISIBLE);
        }
        holder.linearLayout.setOnClickListener(new ItemListen(position));
        return convertView;
    }

    class MyViewHolder {
        private TextView tv;
        private ImageView iv;
        private LinearLayout linearLayout;
    }

    class ItemListen implements View.OnClickListener {
        private int position;
        public ItemListen(int pos) {
            position = pos;
        }
        @Override
        public void onClick(View v) {
            if (position < 6) {

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("list", position);
                context.startActivity(intent);

            }
            if (position == 6) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("确定退出吗?");
                builder.setTitle("提示");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = context.getSharedPreferences("loginUser", context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);


                        dialog.dismiss();


                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }


        }
    }
}
