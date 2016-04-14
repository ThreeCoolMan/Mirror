package com.lanou.mirror.listener;

/**
 * Created by Yi on 16/3/31.
 */
public interface OkHttpNetHelperListener<T> {

    /**
     * 请求成功回调
     *
     * @param result 请求成功结果 String 类型
     * @param bean   请求成功结果 实体类
     */
    void requestSucceed(String result, T bean);

    /**
     * 请求失败回调
     *
     * @param cause 请求失败原因
     */
    void requestFailed(String cause);

}
