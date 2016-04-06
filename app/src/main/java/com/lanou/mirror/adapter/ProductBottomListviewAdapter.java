package com.lanou.mirror.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.bean.GoodsListBean;
import com.lanou.mirror.tools.OkHttpNetHelper;

/**
 * Created by dllo on 16/4/1.
 */
public class ProductBottomListviewAdapter extends BaseAdapter {

    GoodsListBean goodsListBean;
    Context context;
    LayoutInflater inflater;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    final int TYPE_4 = 3;

    public ProductBottomListviewAdapter(Context context, GoodsListBean goodsListBean) {
        this.context = context;
        this.goodsListBean = goodsListBean;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return    goodsListBean.getData().getList().get(0).getDesign_des().size()+3;

    }


    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p == 0) {
            return TYPE_1;
        } else if (p == 1) {
            return TYPE_2;
        } else if (p == 2) {
            return TYPE_4;
        } else if (p>2){
            return TYPE_3;
        }return TYPE_2;
    }

    //设置当前listview
    @Override
    public int getViewTypeCount() {
        return 4;
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
        GoodsDescriptionViewHolder holderDes = null;
        ImageViewHolder holderI = null;
        GoodNameViewHolder holderGoodName = null;
        int type = getItemViewType(position);

            switch (type) {
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.item_product_bottom_listview_description, parent, false);
                    holderDes = new GoodsDescriptionViewHolder(convertView);
                    holderDes.brandTv.setText(goodsListBean.getData().getList().get(0).getBrand());
                    holderDes.nameTv.setText(goodsListBean.getData().getList().get(0).getGoods_name());
                    holderDes.infodesTv.setText(goodsListBean.getData().getList().get(0).getInfo_des());
                    holderDes.priceTv.setText(goodsListBean.getData().getList().get(0).getGoods_price());
                    convertView.setTag(holderDes);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.item_product_bottom_listview_null, parent, false);

                    break;
                case TYPE_3:
                    String url = null;
                    url = goodsListBean.getData().getList().get(0).getDesign_des().get(position-3).getImg();
                    convertView = inflater.inflate(R.layout.item_product_bottom_listview_image, parent, false);
                    holderI = new ImageViewHolder(convertView);
                    OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, holderI.imageView);

                    break;
                case TYPE_4:
                    convertView = inflater.inflate(R.layout.item_product_bottom_listview_goodsname, parent, false);
                    holderGoodName = new GoodNameViewHolder(convertView);
                    holderGoodName.goodsBrandTv.setText(goodsListBean.getData().getList().get(0).getBrand());
            }


        return convertView;

    }

    public FrameLayout getFl(View view) {
        GoodsDescriptionViewHolder holderF = new GoodsDescriptionViewHolder(view);
        holderF.fl = (FrameLayout) inflater.inflate(R.layout.item_product_bottom_listview_description, null).findViewById(R.id.item_product_bottom_fl);
        return holderF.fl;
    }

    class GoodsDescriptionViewHolder {

        private TextView nameTv, brandTv, infodesTv, priceTv;
        private FrameLayout fl;

        public GoodsDescriptionViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_name);
            brandTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_brand);
            infodesTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_info_des);
            priceTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_goods_price);
        }
    }


    class ImageViewHolder {

        private ImageView imageView;

        public ImageViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.item_product_bottom_imageview_iv);
        }
    }

    class GoodNameViewHolder {
        private TextView goodsBrandTv;

        public GoodNameViewHolder(View view) {
            goodsBrandTv = (TextView) view.findViewById(R.id.item_product_bottom_tv_goodsname);
        }
    }
}



