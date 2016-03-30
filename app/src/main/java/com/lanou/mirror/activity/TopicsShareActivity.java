package com.lanou.mirror.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.TopicsShareAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.fragment.TopicsShareCaseFragment;
import com.lanou.mirror.tools.VerticalPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yi on 16/3/30.
 */
public class TopicsShareActivity extends BaseActivity {

    private VerticalPager verticalPager; //竖向滑动页面
    private TopicsShareAdapter adapter; //适配器
    private RelativeLayout relativeLayout; //activity 的线性布局
    private List<Fragment> data = new ArrayList<>();//这是复用的 fragment 集合测试使用
    private List<Drawable> drawables = new ArrayList<>();//这是切换的背景 Drawable 集合


    @Override
    protected int setContent() {
        return R.layout.activity_topicsshare;

    }

    @Override
    protected void initView() {
        verticalPager = bindView(R.id.topicsShare_verticalPager);
        relativeLayout = bindView(R.id.topicsShare_relativeLayout);

    }

    @Override
    protected void initData() {
        //测试用图片数据(以后删掉)
        drawables.add(getResources().getDrawable(R.mipmap.btn_wear_atlas));
        drawables.add(getResources().getDrawable(R.mipmap.btn_shopping));
        drawables.add(getResources().getDrawable(R.mipmap.ic_launcher));
        drawables.add(getResources().getDrawable(R.mipmap.iv_blog_icon));
        drawables.add(getResources().getDrawable(R.mipmap.iv_display_page));

        for (int i = 0; i < 5; i++) {
            //复用 fragment 时进行 bundle 传值
            data.add(getTopicsShareCaseFragment());
        }

        adapter = new TopicsShareAdapter(getSupportFragmentManager(), data);
        verticalPager.setAdapter(adapter);

        verticalPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //verticalPager 滑动监听,当改变页面时 更换线性布局背景
                relativeLayout.setBackground(drawables.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    //自定义方法复用 fragment 时进行 bundle 传值
    private Fragment getTopicsShareCaseFragment() {

        Bundle bundle = new Bundle();
        //bundle.putString("~", "~");
        Fragment caseFragment = new TopicsShareCaseFragment();
        caseFragment.setArguments(bundle);
        return caseFragment;
    }
}
