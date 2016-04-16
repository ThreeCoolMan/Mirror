package com.lanou.mirror.tools;

import android.util.Log;

/**
 * Created by dllo on 16/4/15.
 */
public class L {
    public static void showLogI(String s, Object object) {
        Log.i(s, object + "");
    }

    public static void showLogV(String s, Object object) {
        Log.v(s, object + "");
    }

    public static void showLogE(String s, Object object) {
        Log.e(s, object + "");
    }

    public static void showLogW(String s, Object object) {
        Log.w(s, object + "");
    }

    public static void showLogD(String s, Object object) {
        Log.d(s, object + "");
    }
}
