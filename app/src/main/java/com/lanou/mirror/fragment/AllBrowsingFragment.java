package com.lanou.mirror.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.AllBrowsingFailedFragmentAdapter;
import com.lanou.mirror.adapter.AllBrowsingFragmentAdapter;
import com.lanou.mirror.adapter.PopupwindowListViewAdapter;
import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.base.BaseFragment;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.bean.TopicsShareBeans;
import com.lanou.mirror.greendao.Cache;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.DaoHelper;
import com.lanou.mirror.tools.DaoSingleton;
import com.lanou.mirror.tools.OkHttpNetHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 何伟东 on 16/3/29.
 */
public class AllBrowsingFragment extends BaseFragment implements OkHttpNetHelperListener<GoodsListBeans>, UrlListener {
    private AllBrowsingFragmentAdapter adapter;
    private AllBrowsingFailedFragmentAdapter failedAdapter;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;
    private int position;
    private DaoHelper daoHelper = new DaoHelper();
    private TextView titleTv;
    String[] titles = BaseApplication.getContext().getResources().getStringArray(R.array.titles);
    private List<Cache> cacheList;
    private Cache cache;
    private int type = 1;
    private String token;

    @Override
    protected int setContent() {
        return R.layout.fragment_allbrowsing;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.fragment_allbrowsing_recyclerview);
        linearLayout = bindView(R.id.fragment_allbrowsing_title_linearlayout);
        titleTv = bindView(R.id.fragment_allbrowsing_title_tv);

    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        position = bundle.getInt("position", 0);
        token = bundle.getString("token");
        titleTv.setText(titles[position]);
        if (position == 3) {
            HashMap<String, String> map = new HashMap<>();
            map.put("device_type", "3");
            final OkHttpNetHelper helper = OkHttpNetHelper.getOkHttpNetHelper();
            linearLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getPopupWindow();
                    // 这里是位置显示方式
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
                }
            });
            helper.postRequest(STORY_LIST_URL, map, TopicsShareBeans.class, new OkHttpNetHelperListener<TopicsShareBeans>() {
                @Override
                public void requestSucceed(String result, final TopicsShareBeans topicsShareBeans) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DaoSingleton.getInstance(getContext()).deleteCacheWithCondition(topicsShareBeans.getData().getList().get(1).getStory_img());
                            adapter = new AllBrowsingFragmentAdapter(topicsShareBeans, position, getContext(), token);
                            cache = new Cache();
                            cache.setTitle(topicsShareBeans.getData().getList().get(1).getStory_title());
                            cache.setUrl(topicsShareBeans.getData().getList().get(1).getStory_img());
                            daoHelper.addData(cache);
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }

                @Override
                public void requestFailed(String result) {
                    cacheList = daoHelper.loadAll();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            failedAdapter = new AllBrowsingFailedFragmentAdapter((ArrayList<Cache>) cacheList, position);
                            LinearLayoutManager manager = new LinearLayoutManager(getContext());
                            manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                            recyclerView.setLayoutManager(manager);
                            recyclerView.setAdapter(failedAdapter);
                        }
                    });

                }
            });

        } else if (position < 3) {
            HashMap<String, String> map = new HashMap<>();
            map.put("device_type", "3");
            map.put("last_time", "");
            map.put("token", "");
            map.put("page", "");
            map.put("category_id", "");
            map.put("version", "");
            OkHttpNetHelper.getOkHttpNetHelper().postRequest(PRODUCTS_GOODS_LIST_URL, map, GoodsListBeans.class, this);
        }
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPopupWindow();
                // 这里是位置显示方式
                popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, 0, 0);
            }
        });
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {

        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // 获取自定义布局文件pop.xml的视图
        View popupWindowView = getActivity().getLayoutInflater().inflate(R.layout.item_fragment_popuwindow, null,
                false);
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        popupWindow = new PopupWindow(popupWindowView, dm.widthPixels, dm.heightPixels - 280, true);
        ListView listView = (ListView) popupWindowView.findViewById(R.id.item_fragment_popupwindow_listview);
        PopupwindowListViewAdapter adapter = new PopupwindowListViewAdapter(getContext(),position);
        listView.setAdapter(adapter);

        // 设置动画效果
        //PopupWindow的动画显示效果是通过setAnimationStyle(int id)方法设置的，
        // 其中id为一个style的id，所以我们要在styles.xml文件中设置一个动画样式
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        // 点击其他地方消失
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    @Override
    public void requestSucceed(String result, final GoodsListBeans goodsListBeans) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                    for (int i = 0; i < goodsListBeans.getData().getList().size(); i++) {
                        DaoSingleton.getInstance(getContext()).deleteCacheWithCondition(goodsListBeans.getData().getList().get(i).getGoods_img());
                        cache = new Cache();
                        cache.setCity(goodsListBeans.getData().getList().get(i).getProduct_area());
                        cache.setBrand(goodsListBeans.getData().getList().get(i).getGoods_name());
                        cache.setDescription(goodsListBeans.getData().getList().get(i).getBrand());
                        cache.setPrice(goodsListBeans.getData().getList().get(i).getGoods_price());
                        cache.setUrl(goodsListBeans.getData().getList().get(i).getGoods_img());
                        daoHelper.addData(cache);

                }
                adapter = new AllBrowsingFragmentAdapter(goodsListBeans, position, getContext(), token);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public void requestFailed(String cause) {
        cacheList = daoHelper.loadAll();
        Log.d("allBroFragment",cacheList.size()+"");

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                failedAdapter = new AllBrowsingFailedFragmentAdapter((ArrayList<Cache>) cacheList, position);
                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(failedAdapter);
            }
        });
    }
}
