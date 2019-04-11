package com.dxy.apk.install;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.dxy.apk.BuildConfig;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * Created by duxueyang on 2019/3/27.
 * Android  安装类
 */

public class InstallUtils {


    /**
     * 安装APK
     */
    public static void installApk(Context context, String apkUrl) {
        if (checkFastInstall(context)) {
            fastInstall(context, apkUrl);
        } else{
            getInstallPermission(context,apkUrl);
        }

    }

    /**
     * 核实安装未知来源权限
     */
    private static boolean checkFastInstall(Context context) {
        if (OSUtils.hasOreo())
            return context.getPackageManager().canRequestPackageInstalls();
        return true;
    }

    /**
     * 检测apk是否存在
     */
    public static boolean checkApkExist(String apkUrl){
        return  new File(Uri.parse(apkUrl).getPath()).exists();
    }


    /**
     * 快速安装
     * 不需要检测未知来源或者已经得到安装未知来源权限
     */
    private static void fastInstall(Context context, String apkUrl) {
        File file = new File(Uri.parse(apkUrl).getPath());
        if (context == null) return;
        if (!file.exists()) return;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //Android 7.0+不能直接通过uri访问
        if (OSUtils.hasNougat()) {
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致  参数3共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID+".FileProvider", file);
            //目标应用临时授权该Uri的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);

    }


    /**
     * Android 8.0+需要用户授权
     */
    private static void getInstallPermission(Context context,String apkUrl ){
        Intent intent = new Intent(context, InstallUnknownResourceActivity.class);
        intent.putExtra(InstallUnknownResourceActivity.APKURL, apkUrl);
        context.startActivity(intent);
    }







}
