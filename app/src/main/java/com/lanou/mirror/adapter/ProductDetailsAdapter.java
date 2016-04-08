package com.lanou.mirror.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.bm.library.PhotoView;
import com.lanou.mirror.bean.GoodsListBeans;
import com.lanou.mirror.listener.ProductDetailsItemListioner;
import com.lanou.mirror.R;
import com.lanou.mirror.tools.OkHttpNetHelper;
import com.universalvideoview.UniversalMediaController;
import com.universalvideoview.UniversalVideoView;

/**
 * Created by dllo on 16/3/30.
 */
public class ProductDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private GoodsListBeans goodsListBeans;
    private ProductDetailsItemListioner listioner;
    public ProductDetailsAdapter(Context context, GoodsListBeans goodsListBeans) {
        this.context = context;
        this.goodsListBeans = goodsListBeans;
        notifyDataSetChanged();

    }

    public void SetDetailsListener(ProductDetailsItemListioner listioner) {
        this.listioner = listioner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != 0) {

            return new ProductDetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_recycleview, parent, false));

        }
        return new VideoProductDetailsHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_details_recycleview_header, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0) {
            OkHttpNetHelper.getOkHttpNetHelper().setOkImage(goodsListBeans.getData().getList().get(0).getWear_video().get(position + 1).getData(),
                    ((ProductDetailsHolder) holder).detailsIv);
        } else
            ((VideoProductDetailsHolder) holder).mVideoView.getId();

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return goodsListBeans.getData().getList().get(0).getWear_video().size() - 1;
    }

    class ProductDetailsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        private LinearLayout linearLayout;
        private PhotoView detailsIv;

        public ProductDetailsHolder(View itemView) {
            super(itemView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_details_ll_details);
            detailsIv = (PhotoView) itemView.findViewById(R.id.item_details_iv_details);
            detailsIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (listioner != null) {
                listioner.productDetailsItemListioner(getItemViewType(), v, goodsListBeans);

            }
        }
    }

    class VideoProductDetailsHolder extends RecyclerView.ViewHolder implements  UniversalVideoView.VideoViewCallback {
        private String SEEK_POSITION_KEY = "SEEK_POSITION_KEY";
        private String VIDEO_URL = null;
        private int position;
        private UniversalVideoView mVideoView;
        private UniversalMediaController mMediaController;
        View mVideoLayout;
        private int mSeekPosition;// 纪录当前视频播放的位置
        private int cachedHeight;

        private boolean isFullscreen;
        private ImageButton mStart;

        public VideoProductDetailsHolder(View itemView) {
            super(itemView);
            mVideoView = (UniversalVideoView) itemView.findViewById(R.id.item_details_uv_details);
            mMediaController = (UniversalMediaController) itemView.findViewById(R.id.item_details_umc_details);
            mStart = (ImageButton) itemView.findViewById(R.id.item_details_ib_details);
            mVideoLayout =itemView.findViewById(R.id.item_details_fl_details);
            mVideoView.setMediaController(mMediaController);
            mStart.getBackground().setAlpha(3);
            mVideoView.setVideoViewCallback(this);
            setVideoAreaSize();

            mStart.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (mSeekPosition > 0) {
                        mVideoView.seekTo(50);
                    }
                    mVideoView.start();
                    mStart.setVisibility(View.GONE);
                }
            });
            mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
        }

        private void setVideoAreaSize() {
            mVideoLayout.post(new Runnable() {
                @Override
                public void run() {
                    int width = mVideoLayout.getWidth();
                    cachedHeight = (int) (width * 405f / 720f);
                    ViewGroup.LayoutParams videoLayoutParams = mVideoLayout.getLayoutParams();
                    videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    videoLayoutParams.height = cachedHeight;
                    mVideoLayout.setLayoutParams(videoLayoutParams);
                    mVideoView.setVideoPath(
                            "http://7xr7f7.com2.z0.glb.qiniucdn.com/Jimmy%20fairly%20-%20Spring%202014-HD.mp4");
                    mVideoView.requestFocus();
                }
            });
        }

        //暂时没走这个方法
        @Override
        public void onScaleChange(boolean isFullscreen) {
            this.isFullscreen = isFullscreen;
            Log.d("尺寸",isFullscreen+"");
            if (isFullscreen) {
                //这个LayoutParams类是用于child view（子视图） 向 parent view（父视图）传达自己的意愿的一个东西
                ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                layoutParams.width = 1200;
                Log.d("尺寸", layoutParams.width +"宽");
                layoutParams.height = 800;
                Log.d("尺寸", layoutParams.height +"长");
                mVideoLayout.setLayoutParams(layoutParams);
                mStart.setVisibility(View.GONE);

            } else {
                ViewGroup.LayoutParams layoutParams = mVideoLayout.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                Log.d("尺寸", layoutParams.width +"宽2");
                layoutParams.height = this.cachedHeight;
                Log.d("尺寸", layoutParams.height +"长2");
                mVideoLayout.setLayoutParams(layoutParams);
                mStart.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPause(MediaPlayer mediaPlayer) {

        }

        @Override
        public void onStart(MediaPlayer mediaPlayer) {

        }

        @Override
        public void onBufferingStart(MediaPlayer mediaPlayer) {

        }

        @Override
        public void onBufferingEnd(MediaPlayer mediaPlayer) {

        }


    }


}
