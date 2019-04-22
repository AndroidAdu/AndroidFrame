package com.dxy.sharesdk.auth;

import java.util.HashMap;

import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;


/**
 * Created by John on 2019/4/18.
 * 授权管理并且获取用户的信息
 */
public class AuthUserInfoHelper {

    //QQ 平台
    public static  final String PLATFORM_QQ= QQ.NAME;
    //微博 平台
    public static  final String PLATFORM_SinaWeibo= SinaWeibo.NAME;
    //微信 平台
    public static  final String PLATFORM_WeChat= Wechat.NAME;
    //Facebook 平台
    public static  final String PLATFORM_FaceBook= Facebook.NAME;

    /**
     * 从平台授权
     * platFormName
     */
    public static void authFromPlatFrom(String platformName, final AuthUserInfoHelperListener authUserInfoHelperListener) {
        Platform platform = ShareSDK.getPlatform(platformName);
        platform.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                String uid = platform.getDb().getUserId();
                String token = platform.getDb().getToken();
                if (authUserInfoHelperListener != null) {
                    authUserInfoHelperListener.getUserInfo(uid, token);
                }
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                if(authUserInfoHelperListener!=null){
                    authUserInfoHelperListener.authError();
                }
            }

            @Override
            public void onCancel(Platform platform, int i) {
                if(authUserInfoHelperListener!=null){
                    authUserInfoHelperListener.authCancel();
                }
            }
        });
        platform.authorize();


    }

}
