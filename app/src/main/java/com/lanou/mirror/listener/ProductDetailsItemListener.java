package com.lanou.mirror.listener;

import android.view.View;

import com.lanou.mirror.bean.GoodsListBeans;

/**
 * Created by dllo on 16/3/30.
 */
public interface ProductDetailsItemListener {

    /**
     * 佩戴图集接口回调方法监听recycleView行布局
     * @param position
     * @param view 被监听的View
     * @param goodsListBeans  返回的实体类
     */
    void productDetailsItemListener(int position,View view,GoodsListBeans goodsListBeans);

}
