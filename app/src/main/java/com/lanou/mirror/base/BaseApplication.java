package com.lanou.mirror.base;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Yi on 16/3/29.
 */
public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Fresco.initialize(context);
    }

    /**
     * BaseApplication静态方法
     *
     * @return 返回 context 对象
     */
    public static Context getContext() {
        return context;
    }
}
