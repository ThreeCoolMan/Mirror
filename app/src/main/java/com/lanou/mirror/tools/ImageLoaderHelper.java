package com.lanou.mirror.tools;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lanou.mirror.R;
import com.lanou.mirror.base.BaseApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;

/**
 * Created by Yi on 16/3/31.
 */
public class ImageLoaderHelper {

    public static ImageLoaderHelper imageLoaderHelper;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;//图片设置对象
    private ImageLoaderConfiguration configuration;//设置缓存属性
    private File cacheDir = BaseApplication.getContext().getFilesDir();//获得磁盘本地储存路径,目标路径 /data/data/工程名/cache


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
        //设置网络加载图片
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(null)//设置加载期间显示的图片
                .showImageForEmptyUri(R.mipmap.blackmoney)//设置空的网址图片
                .showImageOnFail(R.mipmap.bg)//设置加载失败图片
                        //.delayBeforeLoading(200) //下载前的延迟时间加载时间
                .cacheInMemory(true)//设置是否缓存在内存中
                .cacheOnDisk(true)//设置是否磁盘缓存
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片解码格式
                .build();
        //设置缓存
        configuration = new ImageLoaderConfiguration.Builder(BaseApplication.getContext())
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheFileCount(100)//设置缓存文件个数
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//设置文件名 MD5 格式加密(解决文件名冲突问题)
                .build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(configuration);

// 设置默认的缓存属性,不需要磁盘缓存可以用这句
//        imageLoader = ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(BaseApplication.getContext()));

    }

    //加载图片方法
    public void loadImage(String url, ImageView imageView) {
        url = url.trim();
        imageLoader.displayImage(url, imageView, options);

        //String disPath = imageLoader.getDiskCache().get(url).getPath();//这句话是获取目标图片的本地路径,测试用的
    }
}
