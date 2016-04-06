package com.lanou.mirror.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.bean.GoodsListBean;

/**
 * Created by dllo on 16/4/1.
 */
public class ProductTopListviewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    final int TYPE_F = 0;
    final int TYPE_S = 1;
    final int TYPE_T = 2;
    private GoodsListBean goodsListBean;

    public ProductTopListviewAdapter(Context context, GoodsListBean goodsListBean) {
        this.context = context;
        this.goodsListBean = goodsListBean;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return goodsListBean.getData().getList().get(0).getGoods_data().size() + 3;
    }


    /**
     * 这里设置前两行行布局和最后两行行布局为透明
     */
    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p <= 1) {
            return TYPE_F;
        } else if (p == 2) {
            return TYPE_T;
        } else {
            return TYPE_S;
        }


    }

    /**
     * 此方法为设置当前listview有几种行布局
     * 如果不写,就会最多显示两种,不知道为什么
     */

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {

        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FirstViewHolder holderF = null;
        SecondViewHolder holderS = null;
        ThirdViewHolder holderT = null;
        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case TYPE_F:
                    convertView = inflater.inflate(R.layout.item_product_top_listview_null, parent, false);
                    break;
                case TYPE_T:
                    convertView = inflater.inflate(R.layout.item_product_top_listview_null_second, parent, false);
                    break;
                case TYPE_S:
                    convertView = inflater.inflate(R.layout.item_product_top_listview_describe, parent, false);
                    holderT = new ThirdViewHolder(convertView);
                    holderT.introContentTv.setText(goodsListBean.getData().getList().get(0).getGoods_data().get(position - 3).getIntroContent());
                    if (goodsListBean.getData().getList().get(0).getGoods_data().get(position - 3).getName().equals("")) {
                        holderT.nameTv.setText(goodsListBean.getData().getList().get(0).getGoods_data().get(position - 3).getCountry());

                    } else {
                        holderT.nameTv.setText(goodsListBean.getData().getList().get(0).getGoods_data().get(position - 3).getName());
                    }

                    break;
            }

        }
        return convertView;

    }

    private class FirstViewHolder {

    }

    private class SecondViewHolder {

    }

    private class ThirdViewHolder {
        private TextView introContentTv, nameTv;


        public ThirdViewHolder(View view) {
            introContentTv = (TextView) view.findViewById(R.id.item_product_tv_introcontent);
            nameTv = (TextView) view.findViewById(R.id.item_product_tv_name);
        }
    }

}

