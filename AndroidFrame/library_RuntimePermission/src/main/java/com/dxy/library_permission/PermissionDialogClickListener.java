package com.dxy.library_permission;

/**
 * Created by John on 2019/3/26.
 */

public interface PermissionDialogClickListener {
    //请求权限成功
    void onSuccess();
    //请求权限失败
    void onFailure();
}
