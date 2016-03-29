package com.lanou.mirror.fragment;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.FragmentAllBrowsingAdapter;
import com.lanou.mirror.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by dllo on 16/3/29.
 */
public class FragmentAllBrowsing extends BaseFragment {
    private ArrayList<String> data;
    private FragmentAllBrowsingAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected int setContent() {
        return R.layout.fragment_allbrowsing;
    }

    @Override
    protected void initView() {
        recyclerView = bindView(R.id.fragment_allbrowsing_recyclerview);
        data = new ArrayList<>();

    }

    @Override
    protected void initData() {
        for (int i =0;i < 20 ; i ++ ){
            data.add(i+ "hahahaah");
            data.add(i + "xixixixixi");
        }
        adapter = new FragmentAllBrowsingAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}
