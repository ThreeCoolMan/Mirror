package com.lanou.mirror.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseApplication;
import com.lanou.mirror.base.BaseFragment;
import com.lanou.mirror.bean.TopicsShareBeans;

import de.greenrobot.event.EventBus;

/**
 * Created by Yi on 16/3/30.
 */
public class TopicsShareCaseFragment extends BaseFragment {

    private TopicsShareBeans beans;//传过来的实体类
    private TextView subTv, titleTv, descriptionTv;
    private int listPosition;
    private int position = 0;

    @Override
    protected int setContent() {
        return R.layout.fragment_topicssharecase;
    }

    @Override
    protected void initView() {
        subTv = bindView(R.id.topicsShareCase_subTitle_tv);
        titleTv = bindView(R.id.topicsShareCase_Title_tv);
        descriptionTv = bindView(R.id.topicsShareCase_description_tv);
    }

    @Override
    protected void initData() {

        Bundle bundle = getArguments();
        beans = bundle.getParcelable("BEANS");
        listPosition = bundle.getInt("POSITION");

        //注册订阅者为当前activity对象
        try {

            EventBus.getDefault().register(this);
        }catch (Exception e){

        }
        //EventBus 回调事假   不能刷新UI

        subTv.setText(beans.getData().getList().get(listPosition).getStory_data().getText_array().get(position).getSmallTitle());
        titleTv.setText(beans.getData().getList().get(listPosition).getStory_data().getText_array().get(position).getTitle());
        descriptionTv.setText(beans.getData().getList().get(listPosition).getStory_data().getText_array().get(position).getSubTitle());
    }

    public void onEvent(int position) {
        this.position = position;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}