package com.lanou.mirror.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.tools.OkHttpNetHelper;

/**
 * Created by dllo on 16/4/1.
 */
public class ProductBottomListViewAdapter extends BaseAdapter {

    GoodsListBeans goodsListBeans;
    Context context;
    LayoutInflater inflater;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    final int TYPE_4 = 3;
    private int pos;

    public ProductBottomListViewAdapter(Context context, GoodsListBeans goodsListBeans, int pos) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
        this.pos = pos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //因为有三行其他种类行布局 所以加3
        return getListEntity().getDesign_des().size() + 3;
    }


    /**
     * 第0行TYPE_1是个半透明带文字的布局
     * 第1行TYPE_2是个大空白
     * 第2行TYPE_4是个商品品牌的小行布局
     * 第3行及之后所有行TYPE_3 是商品图片
     */
    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p == 0) {
            return TYPE_1;
        } else if (p == 1) {
            return TYPE_2;
        } else if (p == 2) {
            return TYPE_4;
        } else if (p > 2) {
            return TYPE_3;
        }
        return TYPE_2;
    }

    //设置当前listview有四种行布局
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
        GoodBrandViewHolder holderGoodName = null;
        int type = getItemViewType(position);

        switch (type) {
            case TYPE_1:
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_description, parent, false);
                holderDes = new GoodsDescriptionViewHolder(convertView);
                holderDes.brandTv.setText(getListEntity().getBrand());
                holderDes.nameTv.setText(getListEntity().getGoods_name());
                holderDes.infodesTv.setText(getListEntity().getInfo_des());
                holderDes.priceTv.setText(getListEntity().getGoods_price());
                convertView.setTag(holderDes);
                break;
            case TYPE_2:
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_null, parent, false);
                break;
            case TYPE_3:
                String url = null;
                url = getListEntity().getDesign_des().get(position - 3).getImg();
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_image, parent, false);
                holderI = new ImageViewHolder(convertView);
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, holderI.imageView);

                break;
            case TYPE_4:
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_goodsname, parent, false);
                holderGoodName = new GoodBrandViewHolder(convertView);
                holderGoodName.goodsBrandTv.setText(getListEntity().getBrand());
        }


        return convertView;

    }

    private GoodsListBeans.DataEntity.ListEntity getListEntity() {
        return goodsListBeans.getData().getList().get(pos);
    }

    //想用来设置顶部透明度变化的
    public FrameLayout getFl(View view) {
        GoodsDescriptionViewHolder holderF = new GoodsDescriptionViewHolder(view);
        holderF.fl = (FrameLayout) inflater.inflate(R.layout.item_product_bottom_listview_description, null).findViewById(R.id.item_product_bottom_fl);
        return holderF.fl;
    }

    //顶部半透明viewHolder
    class GoodsDescriptionViewHolder {

        private TextView nameTv, brandTv, infodesTv, priceTv;
        private FrameLayout fl;//用来设置顶部透明度变化

        public GoodsDescriptionViewHolder(View view) {
            nameTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_name);
            brandTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_brand);
            infodesTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_info_des);
            priceTv = (TextView) view.findViewById(R.id.item_product_bottom_description_tv_goods_price);
        }
    }


    //多行图片的viewHolder
    class ImageViewHolder {
        private ImageView imageView;
        public ImageViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.item_product_bottom_imageview_iv);
        }
    }

    //商品品牌viewHolder
    class GoodBrandViewHolder {
        private TextView goodsBrandTv;
        public GoodBrandViewHolder(View view) {
            goodsBrandTv = (TextView) view.findViewById(R.id.item_product_bottom_tv_goodsname);
        }
    }
}



