package com.lanou.mirror.tools;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lanou.mirror.R;

/**
 * Created by dllo on 16/4/1.
 */
public class LinkageListView extends FrameLayout {
    /**
     * 1.我们的自定义控件和其他的控件一样,应该写成一个类,而这个类的属性是是有自己来决定的.
     * <p/>
     * 2.我们要在res/values目录下建立一个attrs.xml的文件,并在此文件中增加对控件的属性的定义.
     * <p/>
     * 3.使用AttributeSet来完成控件类的构造函数,并在构造函数中将自定义控件类中变量与attrs.xml中的属性连接起来.
     * <p/>
     * 4.在自定义控件类中使用这些已经连接的属性变量.
     * <p/>
     * 5.将自定义的控件类定义到布局用的xml文件中去.
     * <p/>
     * 6.在界面中生成此自定义控件类对象,并加以使用.
     */

    private static final float LINKAGE_SPEED = 2.5f;

    private float linkageSpeed;

    private ListView mBottomListView, mTopListView;

    private Context mContext;

    private BaseAdapter mBotAdapter, mTopAdapter;

    private int headerHeight;

    private boolean limit = true;//boolean 变量用于判断动画是否显示

    public LinkageListView(Context context) {
        this(context, null);
    }

    public LinkageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinkageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LinkageListView);
        // 取出对应的值设置给color,并提供一个默认值
        linkageSpeed = a.getFloat(R.styleable.LinkageListView_linkageSpeed, LINKAGE_SPEED);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initLayout(context);
        linkage();
    }

    private void initLayout(Context context) {
        mBottomListView = new ListView(mContext);
        mTopListView = new ListView(mContext);
        mBottomListView.setVerticalScrollBarEnabled(true);
        mTopListView.setVerticalFadingEdgeEnabled(true);
        mBottomListView.setDivider(null);
        mBottomListView.setDividerHeight(0);//去掉分割线
        mTopListView.setDivider(null);//一样
        mTopListView.setDividerHeight(0);
        mBottomListView.setSelector(android.R.color.transparent);
        mTopListView.setSelector(android.R.color.transparent);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mBottomListView, params);
        addView(mTopListView, params);
    }


    /**
     * 联动两个ListView
     */
    private void linkage() {
        // 触摸事件传递.
        mTopListView.setOnTouchListener(new OnTouchListener() {
            /**
             * MotionEvent :超牛B的一个类
             */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /**
                 * dispatchTouchEvent作用是将touch事件向下传递直到遇到被触发的目标view,
                 * 如果返回true,表示当前view就是目标view,事件停止向下分发。
                 * 否则返回false,表示当前view不是目标view,需要继续向下分发寻找目标view.
                 * 这个方法也可以被重载，手动分配事件。
                 */
                //点击传递给下一个view 即下一个listview
                return mBottomListView.dispatchTouchEvent(event);
            }
        });

        /**
         *  AbsListView: android系统定义一个抽象列表控件,用于给列表控件去实现的例如ListView GridView等等
         */
        // 联动
        mBottomListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            /**
             * @param firstVisibleItem  第一个可见的行的索引位置
             * @param visibleItemCount  可见的item个数
             * @param totalItemCount    总行数
             */
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View child = view.getChildAt(0);
                if (child != null) {
                    mTopListView.setSelectionFromTop(firstVisibleItem, (int) (child.getTop() * linkageSpeed));
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
    }

    public void setAdapter(BaseAdapter botAdapter, BaseAdapter topAdapter) {
        mBotAdapter = botAdapter;
        mTopAdapter = topAdapter;
        mBottomListView.setAdapter(mBotAdapter);
        mTopListView.setAdapter(mTopAdapter);
    }

    public void setLinkageSpeed(float linkageSpeed) {
        this.linkageSpeed = linkageSpeed;
    }

    /**
     * 自定义方法实现组件滑动监听
     *
     * @param relativeLayout 设置隐藏或者显示的相对布局
     * @author Yi
     */
    public void setLinkListViewListener(final RelativeLayout relativeLayout) {
        //表层 listView监听 当滑到位置显示动画,滑会位置隐藏动画
        mTopListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int position = mTopListView.getLastVisiblePosition();
                if (position >= 4 && limit == true) {
                    relativeLayout.setVisibility(VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(relativeLayout, "translationX", -1200, 0);
                    animator.setDuration(500);
                    animator.start();
                    limit = false;
                } else if (position < 4 && limit == false) {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(relativeLayout, "translationX", 0, -1200);
                    animator.setDuration(500);
                    animator.start();
                    limit = true;
                }

            }
        });
    }
}
