package com.lanou.mirror.activity;

import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.ProductBottomListviewAdapter;
import com.lanou.mirror.adapter.ProductTopListviewAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.GoodsListBean;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.LinkageListView;
import com.lanou.mirror.tools.OkHttpNetHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by dllo on 16/4/1.
 */
public class ProductActivity extends BaseActivity implements UrlListener, OkHttpNetHelperListener<GoodsListBean> {
    private LinkageListView mlistView;
    private ProductBottomListviewAdapter bottomAdapter;

    private static List bottomDatas = new ArrayList();
    private static List topDatas = new ArrayList();
    private ImageView backGroundIv;
    private GoodsListBean data = null;

    private FrameLayout frameLayout = null;

    @Override
    protected int setContent() {
        return R.layout.activity_product;

    }

    @Override
    protected void initView() {


        mlistView = bindView(R.id.activity_prodoct_llv);
        backGroundIv = bindView(R.id.activity_product_iv_background);




    }

    @Override
    protected void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("token", "");
        params.put("device_type", "3");
        params.put("page", "");
        params.put("last_time", "");
        params.put("category_id", "");
        params.put("version", "");
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(PRODUCTS_GOODS_LIST_URL, params, GoodsListBean.class, this);







    }


    @Override
    public void requestSucceed(String result, final GoodsListBean bean) {


        runOnUiThread(new Runnable() {//主线程刷新ui
            @Override
            public void run() {
                String url = bean.getData().getList().get(0).getGoods_img();
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, backGroundIv);

                //自定义组件中的方法 只需要添加两个adapter参数即可
                bottomAdapter = new ProductBottomListviewAdapter(getApplication(), bean);
                mlistView.setAdapter(bottomAdapter, new ProductTopListviewAdapter(getApplication(),bean));
                mlistView.setLinkageSpeed(1.2f);//设置当前listview的滑动速度,封装好的方法
            }
        });

    }

    @Override
    public void requestFailed(String cause) {

    }

}
