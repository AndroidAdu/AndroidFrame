package com.dxy.library_permission.runTime;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;

/**
 * Created by John on 2019/3/25.
 * 权限帮助类
 */

public class PermissionUtils {

    //相机权限
    public static final String CAMERA = Manifest.permission.CAMERA;
    //电话权限
    public static final String CALL = Manifest.permission.CALL_PHONE;
    //读写储存权限
    public static final String WRITE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String READ = Manifest.permission.READ_EXTERNAL_STORAGE;
    //联系人权限
    public static final String CONTACTS = Manifest.permission.READ_CONTACTS;
    //位置权限
    public static final String LOCATION = Manifest.permission.LOCATION_HARDWARE;


    /**
     * 检查权限
     */
    public static boolean checkPermissions(Context context, String permission) {
        //API 23以下 默认获取所有权限
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M)
            return true;
        int permissionStatus = ContextCompat.checkSelfPermission(context, permission);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED)
            return true;
        return false;
    }

    /**
     * 请求权限
     * main
     *
     */
    public static void requestPermissions(Context context, String permission, PermissionDialogClickListener listener) {
        if(checkPermissions(context,permission)) {
            listener.onSuccess();
            return;
        }
        String[] permissions;
        switch (permission){
            case WRITE:
                permissions = new String[2];
                permissions[0]=WRITE;
                permissions[1]=READ;
                break;
            case READ:
                permissions = new String[2];
                permissions[0]=WRITE;
                permissions[1]=READ;
                break;
            default:
                permissions = new String[1];
                permissions[0] = permission;
                break;
        }
        requestPermissions(context,permissions,listener);
    }
    

    private static void requestPermissions(Context context,String[] permission,PermissionDialogClickListener listener) {

        /**
         *  v7.AppCompatActivity extends android.app.Activity
         *  PermissionFragment extends android.app.DialogFrament ,DialogFragment is existed in  API 11
         *  min sdk is API 17
         */
        if(context instanceof Activity){
            PermissionFragment permissionFragment = new PermissionFragment();
            permissionFragment.setPermissionListener(listener);
            Bundle bundle=new Bundle();
            bundle.putStringArray(PermissionFragment.PermissionKEY,permission);
            permissionFragment.setArguments(bundle);
            permissionFragment.show(((Activity) context).getFragmentManager(),"");
        }
    }


}
