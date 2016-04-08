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
import com.lanou.mirror.bean.GoodsDetailsBean;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.tools.OkHttpNetHelper;

/**
 * Created by dllo on 16/4/1.
 */
public class ProductBottomListviewAdapter extends BaseAdapter {

    GoodsDetailsBean goodsDetailsBean;
    Context context;
    LayoutInflater inflater;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    final int TYPE_3 = 2;
    final int TYPE_4 = 3;
    final int TYPE_5 = 4;
    private int pos;

    public ProductBottomListviewAdapter(Context context, GoodsDetailsBean goodsDetailsBean, int pos) {
        this.context = context;
        this.goodsDetailsBean = goodsDetailsBean;
        this.pos = pos;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {

        return goodsDetailsBean.getData().getDesign_des().size() + 4;
    }

    /**
     * 第0行TYPE_1是个半透明带文字的布局
     * 第1行TYPE_2是个大空白
     * 第2行TYPE_4是个商品品牌的小行布局
     * 第3行是因为要单独抓取数据所以加了一行行布局
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
        } else if (p == 3) {
            return TYPE_5;
        } else if (p > 3) {
            return TYPE_3;
        }
        return TYPE_2;
    }

    //设置当前listview有五种行布局
    @Override
    public int getViewTypeCount() {
        return 5;
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
        TopImageViewHolder holderTop = null;
        int type = getItemViewType(position);
        switch (type) {
            case TYPE_1:
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_description, parent, false);
                holderDes = new GoodsDescriptionViewHolder(convertView);
                holderDes.brandTv.setText(goodsDetailsBean.getData().getBrand());
                holderDes.nameTv.setText(goodsDetailsBean.getData().getGoods_name());
                holderDes.infodesTv.setText(goodsDetailsBean.getData().getInfo_des());
                holderDes.priceTv.setText(goodsDetailsBean.getData().getGoods_price());
                convertView.setTag(holderDes);
                break;
            case TYPE_2:
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_null, parent, false);
                break;
            case TYPE_3:
                String url = null;
                url = goodsDetailsBean.getData().getDesign_des().get(position - 4).getImg();
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_image, parent, false);
                holderI = new ImageViewHolder(convertView);
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, holderI.imageView);
                break;
            case TYPE_4:
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_goodsname, parent, false);
                holderGoodName = new GoodBrandViewHolder(convertView);
                holderGoodName.goodsBrandTv.setText(goodsDetailsBean.getData().getBrand());
                break;
            case TYPE_5:
                String topUrl = null;
                topUrl = goodsDetailsBean.getData().getGoods_pic();
                convertView = inflater.inflate(R.layout.item_product_bottom_listview_top_image, parent, false);
                holderTop = new TopImageViewHolder(convertView);
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(topUrl, holderTop.topIv);

        }
        return convertView;
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

    class TopImageViewHolder {
        private ImageView topIv;

        public TopImageViewHolder(View view) {
            topIv = (ImageView) view.findViewById(R.id.item_product_bottom_imageview_top_iv);
        }
    }
}



