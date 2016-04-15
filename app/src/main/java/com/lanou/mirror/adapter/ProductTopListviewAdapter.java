package com.lanou.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.bean.GoodsListBeans;

/**
 * Created by dllo on 16/4/1.
 */
public class ProductTopListviewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    final int TYPE_F = 0;
    final int TYPE_S = 1;
    final int TYPE_T = 2;
    private GoodsListBeans goodsListBeans;
    private int pos;

    public ProductTopListviewAdapter(Context context, GoodsListBeans goodsListBeans, int pos) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
        this.pos = pos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //因为有三行是空的行布局 所以加3
        return goodsListBeans.getData().getList().get(0).getGoods_data().size() + 3;
    }


    /**
     * 这里设置前三行是透明行布局
     * 余下都是商品介绍的行布局
     */
    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p <= 1 || p == goodsListBeans.getData().getList().get(0).getGoods_data().size() + 2) {
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

        GoodsIntroduceHolder holderT = null;
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
                    holderT = new GoodsIntroduceHolder(convertView);
                    holderT.introContentTv.setText(goodsListBeans.getData().getList().get(pos).getGoods_data().get(position - 3).getIntroContent());
                    if (goodsListBeans.getData().getList().get(pos).getGoods_data().get(position - 3).getName().equals("")) {
                        holderT.nameTv.setText(goodsListBeans.getData().getList().get(pos).getGoods_data().get(position - 3).getCountry());
                    } else {
                        holderT.nameTv.setText(goodsListBeans.getData().getList().get(pos).getGoods_data().get(position - 3).getName());
                    }
                    break;
            }

        }
        return convertView;

    }


    //商品介绍的ViewHolder
    private class GoodsIntroduceHolder {
        private TextView introContentTv, nameTv;
        public GoodsIntroduceHolder(View view) {
            introContentTv = (TextView) view.findViewById(R.id.item_product_tv_introcontent);
            nameTv = (TextView) view.findViewById(R.id.item_product_tv_name);
        }
    }

}

