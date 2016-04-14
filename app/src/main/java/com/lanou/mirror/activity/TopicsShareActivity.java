package com.lanou.mirror.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * Created by Yi on 16/3/30.
 */
public class TopicsShareActivity extends BaseActivity implements OkHttpNetHelperListener<TopicsShareBeans>, UrlListener {

    private VerticalPager verticalPager; //竖向滑动页面
    private TopicsShareAdapter adapter; //适配器
    private List<Fragment> data = new ArrayList<>();//这是复用的 fragment 集合测试使用
    private HashMap<String, String> params;//请求参数
    private TopicsShareBeans beans;//实体类
    private ImageView backgroundIv,shareIv;//背景 iv
    private int listPosition = 1;//TODO 跳转时传递的 position 用来确认显示的数据集合位置

    @Override
    protected int setContent() {
        return R.layout.activity_topicsshare;

    }

    @Override
    protected void initView() {
        verticalPager = bindView(R.id.topicsShare_verticalPager);
        backgroundIv = bindView(R.id.activity_topicsShare_iv_background);
        shareIv = bindView(R.id.activity_topicsShare_iv_share);


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
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(url, backgroundIv);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        shareIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });

    }

    @Override
    public void requestSucceed(String result, final TopicsShareBeans bean) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                beans = bean;
                OkHttpNetHelper.getOkHttpNetHelper().setOkImage(bean.getData().getList().get(listPosition).getStory_data().getImg_array().get(0), backgroundIv);
                for (int i = 0; i < bean.getData().getList().get(listPosition).getStory_data().getImg_array().size(); i++) {
                    //复用 fragment 时进行 bundle 传值
                    data.add(getTopicsShareCaseFragment(i));
                    adapter.notifyDataSetChanged();//刷新适配器
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
        bundle.putInt("ListPosition", listPosition);
        bundle.putInt("Position", position);
        Fragment caseFragment = new TopicsShareCaseFragment();
        caseFragment.setArguments(bundle);
        return caseFragment;
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("sdsdsd");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }
}
