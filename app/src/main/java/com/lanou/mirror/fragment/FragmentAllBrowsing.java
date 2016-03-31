package com.lanou.mirror.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.FragmentAllBrowsingAdapter;
import com.lanou.mirror.adapter.PopupwindowListViewAdapter;
import com.lanou.mirror.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by 何伟东 on 16/3/29.
 */
public class FragmentAllBrowsing extends BaseFragment {
    private ArrayList<String> data;
    private FragmentAllBrowsingAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;
    private PopupWindow popupWindow;

    @Override
    protected int setContent() {
        return R.layout.fragment_allbrowsing;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.fragment_allbrowsing_recyclerview);
        linearLayout = bindView(R.id.fragment_allbrowsing_title_linearlayout);
        data = new ArrayList<>();

    }

    @Override
    protected void initData() {
        for (int i = 0; i < 20; i++) {
            data.add(i + "hahahaah");
            data.add(i + "xixixixixi");
        }
        adapter = new FragmentAllBrowsingAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
        popupWindow = new PopupWindow(popupWindowView, dm.widthPixels, dm.heightPixels - 300, true);
        ListView listView = (ListView) popupWindowView.findViewById(R.id.item_fragment_popupwindow_listview);
        PopupwindowListViewAdapter adapter = new PopupwindowListViewAdapter(getContext());
        listView.setAdapter(adapter);

        // 设置动画效果
        //PopupWindow的动画显示效果是通过setAnimationStyle(int id)方法设置的，
        // 其中id为一个style的id，所以我们要在styles.xml文件中设置一个动画样式
        //popupWindow.setAnimationStyle(R.style.popWindow_anim);
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

}
