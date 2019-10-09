package com.dxy.facebook.share;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dxy.facebook.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


/**
 * 分享到FB
 */
public class ShareFBActivity extends AppCompatActivity {

    private String TAG=getClass().getSimpleName();

    //整体
    private CallbackManager callbackManager;
    //分享组件
    private ShareDialog shareDialog;

    private TextView tv_sharefb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_fb);
        //初始化callMananger
        initShare();
        initView();


    }

    private void initView() {
        tv_sharefb=findViewById(R.id.tv_sharefb);
        tv_sharefb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareLinkContent content=getLinkContent2();
                if (shareDialog.canShow(content, ShareDialog.Mode.AUTOMATIC)) {
                    shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                }else{
                    Toast.makeText(ShareFBActivity.this, "",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initShare() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        FacebookCallback<Sharer.Result> callback =
                new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onCancel() {
                        Log.d(TAG, "Canceled");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(TAG, String.format("Error: %s", error.toString()));
                    }

                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Log.d(TAG, "Success!");
                    }
                };
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, callback);
    }








    private ShareLinkContent getLinkContent2(){
        return  new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setContentTitle("呵呵呵呵")
                .setShareHashtag(new ShareHashtag.Builder()
                        .setHashtag("#ConnectTheWorld")
                        .build())
                .build();

    }

    private ShareLinkContent getLinkContent3(){
        return  new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .setQuote("Connect on a global scale.")
                .build();

    }





    /**
     * 登录的返回状态
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



}
