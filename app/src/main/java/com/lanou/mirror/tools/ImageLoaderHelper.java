package com.lanou.mirror.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseApplication;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Yi on 16/3/31.
 */
public class ImageLoaderHelper {

    public static ImageLoaderHelper imageLoaderHelper;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;//图片设置对象
    private ImageLoaderHelper() {
        init();//配置imageLoader信息
    }
    public static ImageLoaderHelper getImageLoaderHelper() {
        if (imageLoaderHelper == null) {
            synchronized (ImageLoaderHelper.class) {
                if (imageLoaderHelper == null) {
                    imageLoaderHelper = new ImageLoaderHelper();
                }
            }
        }
        return imageLoaderHelper;
    }


    private void init() {
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.btn_close)//设置空的网址图片
                .showImageOnFail(R.mipmap.blackmoney)//设置加载失败图片
                .delayBeforeLoading(200) //加载时间
                .cacheInMemory(true)//缓存
                .cacheOnDisk(true)//磁盘缓存
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片解码格式
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(BaseApplication.getContext()));
    }

    //加载图片方法
    public void loadImage(String url, ImageView imageView) {
        url = url.trim();
        imageLoader.displayImage(url, imageView, options);
    }
}
