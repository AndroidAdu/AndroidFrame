package com.dxy.sharesdk.share.platform;


import cn.sharesdk.facebook.Facebook;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by yjin on 2017/6/22.
 */

public class FacebookShare {


	/**
	 * 分享网页
	 */
	public void shareWebPage(String webUrl,PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(Facebook.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setUrl(webUrl);
		shareParams.setShareType(Platform.SHARE_WEBPAGE);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

}
