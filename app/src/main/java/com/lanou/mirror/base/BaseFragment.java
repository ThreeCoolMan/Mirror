package com.lanou.mirror.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Yi on 16/3/29.
 */
public abstract class BaseFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(setContent(), container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract int setContent();//绑定布局

    protected abstract void initView();//绑定组件抽象方法

    protected abstract void initData();//加载数据抽象方法

    /**
     * 绑定组件方法
     * @param id 组件 id
     * @param <T> 组件类型
     * @return 返回组件绑定
     */
    protected <T extends View> T bindView(int id) {
        return (T) view.findViewById(id);
    }
}
