package com.lanou.mirror.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.lanou.mirror.R;
import com.lanou.mirror.adapter.TopicsShareAdapter;
import com.lanou.mirror.base.BaseActivity;
import com.lanou.mirror.bean.TopicsShareBeans;
import com.lanou.mirror.fragment.TopicsShareCaseFragment;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.lanou.mirror.listener.UrlListener;
import com.lanou.mirror.tools.OkHttpNetHelper;
import com.lanou.mirror.tools.VerticalPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Yi on 16/3/30.
 */
public class TopicsShareActivity extends BaseActivity implements OkHttpNetHelperListener<TopicsShareBeans>, UrlListener {

    private VerticalPager verticalPager; //竖向滑动页面
    private TopicsShareAdapter adapter; //适配器
    private List<Fragment> data = new ArrayList<>();//这是复用的 fragment 集合测试使用
    private HashMap<String, String> params;//请求参数
    private TopicsShareBeans beans;//实体类
    private ImageView backIv;//背景 iv
    private int listPosition = 1;//TODO 跳转时传递的 position 用来确认显示的数据集合位置


    @Override
    protected int setContent() {
        return R.layout.activity_topicsshare;

    }

    @Override
    protected void initView() {
        verticalPager = bindView(R.id.topicsShare_verticalPager);
        backIv = bindView(R.id.activity_topicsShare_iv_background);


    }

    @Override
    protected void initData() {
        //请求参数
        params = new HashMap<>();
        params.put("token", "");
        params.put("uid", "");
        params.put("device_type", "3");
        params.put("page", "");
        params.put("last_time", "");
        //网络请求
        OkHttpNetHelper.getOkHttpNetHelper().postRequest(STORY_LIST_URL, params, TopicsShareBeans.class, this);

//        for (int i = 0; i < 5; i++) {
//            //复用 fragment 时进行 bundle 传值
//            data.add(getTopicsShareCaseFragment());
//        }

        adapter = new TopicsShareAdapter(getSupportFragmentManager(), data);
        verticalPager.setAdapter(adapter);

        verticalPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //verticalPager 滑动监听,当改变页面时 更换线性布局背景
                String url = beans.getData().getList().get(listPosition).getStory_data().getImg_array().get(position);
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, backIv);

                EventBus.getDefault().post(position);


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    @Override
    public void requestSucceed(String result, final TopicsShareBeans bean) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                beans = bean;
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(bean.getData().getList().get(1).getStory_data().getImg_array().get(0), backIv);
                for (int i = 0; i < bean.getData().getList().get(listPosition).getStory_data().getImg_array().size(); i++) {
                    //复用 fragment 时进行 bundle 传值
                    data.add(getTopicsShareCaseFragment(i));
                    adapter.notifyDataSetChanged();
                }
            }

        });

    }

    @Override
    public void requestFailed(String cause) {

    }

    //自定义方法复用 fragment 时进行 bundle 传值
    private Fragment getTopicsShareCaseFragment(int position) {

        Bundle bundle = new Bundle();
        bundle.putParcelable("BEANS", beans);
        bundle.putInt("POSITION", listPosition);
       // bundle.putInt("");
        Fragment caseFragment = new TopicsShareCaseFragment();
        caseFragment.setArguments(bundle);
        return caseFragment;
    }
}
