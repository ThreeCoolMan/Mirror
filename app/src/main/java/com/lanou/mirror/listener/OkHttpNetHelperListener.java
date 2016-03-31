package com.lanou.mirror.listener;

/**
 * Created by Yi on 16/3/31.
 */
public interface OkHttpNetHelperListener<T> {

    //成功回调
    void requestSucceed(String result, T bean);

    //失败回调
    void requestFailed(String cause);

}
