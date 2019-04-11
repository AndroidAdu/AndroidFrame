package com.dxy.apk.install;

import android.os.Build;

/**
 * Created by duxueyang on 2019/4/2.
 * 系统帮助类
 */
public class OSUtils {

    public static boolean hasKitKat() {return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;}

    public static boolean hasLollipop() { return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP; }

    public static boolean isKitKat() {
        return Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT;
    }

    public static boolean hasMarshmallow() {return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;}

    public static boolean hasNougat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean hasOreo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

}
