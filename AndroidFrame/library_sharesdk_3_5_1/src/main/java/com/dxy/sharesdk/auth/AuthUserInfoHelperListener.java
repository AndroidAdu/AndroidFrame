package com.dxy.sharesdk.auth;

/**
 * Created by John on 2019/4/18.
 */

public interface AuthUserInfoHelperListener {

    //获取用户信息
    void getUserInfo(String uid, String token);

    //失败
    void authError();

    //取消授权
    void authCancel();
}
