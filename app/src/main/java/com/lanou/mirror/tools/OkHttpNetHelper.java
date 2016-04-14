package com.lanou.mirror.tools;

import android.widget.ImageView;

import com.google.gson.Gson;
import com.lanou.mirror.listener.OkHttpNetHelperListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Yi on 16/3/31.
 */
public class OkHttpNetHelper {

    public static OkHttpNetHelper okHttpNetHelper;//静态类对象
    private OkHttpClient okHttpClient;//okHttp 对象
    private Request request; //请求对象
    private Gson gson;
    private FormEncodingBuilder formEncodingBuilder;//编码形式建立对象
    private String result;
    private ImageLoaderHelper imageLoaderHelper;//图片加载工具类

    //私有化构造方法
    private OkHttpNetHelper() {
        okHttpClient = new OkHttpClient();
        gson = new Gson();
        formEncodingBuilder = new FormEncodingBuilder();
        imageLoaderHelper = ImageLoaderHelper.getImageLoaderHelper();
    }

    /**
     * 单例网络请求类对象
     *
     * @return 返回网络请求类对象
     */
    public static OkHttpNetHelper getOkHttpNetHelper() {
        if (okHttpNetHelper == null) {
            synchronized (OkHttpNetHelper.class) {
                if (okHttpNetHelper == null) {
                    okHttpNetHelper = new OkHttpNetHelper();
                }
            }
        }
        return okHttpNetHelper;
    }

    /**
     * Post 网络请求
     *
     * @param url      网络地址
     * @param param    Post 请求参数
     * @param clazz    实体类对象
     * @param listener 网络请求类回调的接口
     * @param <T>      实体类泛型
     */
    public <T> void postRequest(String url, HashMap<String, String> param, final Class<T> clazz, final OkHttpNetHelperListener listener) {

        // Set<String> set = param.keySet();
        //使用增强 for 循环遍历参数 map 集合 ,增强 for 循环不能直接遍历集合对象必须转换成 set 对象
        for (String key : param.keySet()) {
            formEncodingBuilder.add(key, param.get(key));
        }
        request = new Request.Builder().url(url).post(formEncodingBuilder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.requestFailed(String.valueOf(request));
            }

            @Override
            public void onResponse(Response response) throws IOException {
                result = response.body().string();//成功请求获得结果
                T bean = gson.fromJson(result, clazz);//解析结果到实体类
                listener.requestSucceed(result, bean);//接口传递结果和实体类
            }
        });
    }

    /**
     * 返回 String 请求结果 用于手解
     *
     * @param url      网址
     * @param param    请求参数
     * @param listener 回调接口
     */
    public void postStringRequest(String url, HashMap<String, String> param, final OkHttpNetHelperListener listener) {
        for (String key : param.keySet()) {
            formEncodingBuilder.add(key, param.get(key));
        }

        request = new Request.Builder().url(url).post(formEncodingBuilder.build()).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.requestFailed("网络请求失败");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                result = response.body().string();//成功请求获得结果
                listener.requestSucceed(result, null);//接口传递结果和实体类
            }
        });
    }

    /**
     * ImageLoader 图片加载方法
     *
     * @param url       图片网址
     * @param imageView 需要显示图片的 ImageView
     */
    public void setOkImage(String url, ImageView imageView) {
        imageLoaderHelper.loadImage(url, imageView);
    }

}
