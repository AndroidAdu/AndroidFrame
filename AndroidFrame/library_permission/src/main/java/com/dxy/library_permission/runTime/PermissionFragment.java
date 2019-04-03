package com.dxy.library_permission.runTime;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.DialogFragment;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dxy.library_permission.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * Created by John on 2019/3/25.
 *  在DialogFragment中获取整个权限结果
 *
 *  权限获取弹窗
 *
 */

public class PermissionFragment extends DialogFragment {

    public static final String PermissionKEY="PermissionArray";
    private  static  final int PermissonCode=1;

    private PermissionDialogClickListener permissionListener;

    public void setPermissionListener(PermissionDialogClickListener permissionListener) {
        this.permissionListener = permissionListener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission_content,container,false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String [] PermissionArray=getArguments().getStringArray(PermissionKEY);
        requestPermission(PermissionArray);
    }


    @Override
    public void onResume() {
        super.onResume();
        //优化项目
        getDialog().getWindow().getDecorView().setVisibility(View.INVISIBLE);
    }

    /**
     * 权限获取结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PermissonCode){
            if(grantResults!=null&&grantResults.length>0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    requestPermissionSuccess();
                }else{
                    requestPermissionFailure();
                }
            }else{
                requestPermissionFailure();

            }

        }
    }

    /**
     * 请求权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(String[] permissions){
        if(permissions==null||permissions.length==0)  return;
        requestPermissions(permissions,PermissonCode);
    }


    /**
     * 请求成功
     */
    private void requestPermissionSuccess(){
        dismiss();
        if(permissionListener!=null){
            permissionListener.onSuccess();
        }
    }

    /**
     * 请求失败
     */
    private void requestPermissionFailure(){
        dismiss();
        if(permissionListener!=null){
            permissionListener.onFailure();
        }

    }




}
