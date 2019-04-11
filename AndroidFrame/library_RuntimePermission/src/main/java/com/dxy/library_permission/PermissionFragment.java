package com.dxy.library_permission;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;


/**
 * Created by duxueyang on 2019/3/25.
 * 在DialogFragment中获取整个权限结果
 * 权限获取弹窗
 */

public class PermissionFragment extends DialogFragment {

    public static final String PermissionKEY = "PermissionArray";
    private static final int PermissonCode = 1;

    private boolean permissionSataus;

    private PermissionDialogClickListener permissionListener;

    public void setPermissionListener(PermissionDialogClickListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permission_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String[] PermissionArray = getArguments().getStringArray(PermissionKEY);
        permissionSataus = ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), PermissionArray[0]);
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
        if (requestCode == PermissonCode) {
            if (grantResults != null && grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    requestPermissionSuccess();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permissions[0]) && !permissionSataus) {
                        rqeustRepeatPermission(permissions[0]);
                    } else {
                        requestPermissionFailure();
                    }
                }
            } else {
                requestPermissionFailure();

            }

        }
    }

    /**
     * 请求权限
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission(String[] permissions) {
        if (permissions == null || permissions.length == 0) return;
        requestPermissions(permissions, PermissonCode);
    }


    /**
     * 请求成功
     */
    private void requestPermissionSuccess() {
        dismiss();
        if (permissionListener != null) {
            permissionListener.onSuccess();
        }
    }

    /**
     * 请求失败
     */
    private void requestPermissionFailure() {
        dismiss();
        if (permissionListener != null) {
            permissionListener.onFailure();
        }

    }

    /**
     * 二次授权
     *
     * @param permission
     */
    private void rqeustRepeatPermission(String permission) {
        String permissionName = PermissionUtils.getPermissionName(permission);
        new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setMessage("此功能需要" + permissionName + "，是否去授权？")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        dialog.dismiss();
                        dismiss();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
                dialog.dismiss();
                requestPermissionFailure();
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.dismiss();
                requestPermissionFailure();
            }
        }).show();
    }


}
