package com.lanou.mirror.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
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
    private RelativeLayout insideRelativeLayout, outsideRelativeLayout;
    private TextView subTv, titleTv, descriptionTv;
    private int listPosition;
    private int position;

    @Override
    protected int setContent() {
        return R.layout.fragment_topicssharecase;
    }

    @Override
    protected void initView() {
        subTv = bindView(R.id.topicsShareCase_subTitle_tv);
        titleTv = bindView(R.id.topicsShareCase_Title_tv);
        descriptionTv = bindView(R.id.topicsShareCase_description_tv);
        insideRelativeLayout = bindView(R.id.fragment_topicsShare_relativeLayout_inside);
        outsideRelativeLayout = bindView(R.id.fragment_topicsShare_relativeLayout_outside);

        outsideRelativeLayout.setOnLongClickListener(new View.OnLongClickListener() {//外层布局管理器长按监听隐藏文字组件
            @Override
            public boolean onLongClick(View v) {
                insideRelativeLayout.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        outsideRelativeLayout.setOnTouchListener(new View.OnTouchListener() {  //设置外层布局管理器 touch 监听抬起显示文字组件
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP://抬起文字组件显示
                        insideRelativeLayout.setVisibility(View.VISIBLE);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        beans = bundle.getParcelable("BEANS");
        listPosition = bundle.getInt("ListPosition");
        position = bundle.getInt("Position");
        subTv.setText(beans.getData().getList().get(listPosition).getStory_data().getText_array().get(position).getSmallTitle());
        titleTv.setText(beans.getData().getList().get(listPosition).getStory_data().getText_array().get(position).getTitle());
        descriptionTv.setText(beans.getData().getList().get(listPosition).getStory_data().getText_array().get(position).getSubTitle());
    }


}