package com.dxy.facebook.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dxy.facebook.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;


/**
 * 包括FacebooK登录以及获取个人公共资料
 */
public class FBDemoActivity extends AppCompatActivity {

    private LoginButton loginButton;

    //登录回调
    private CallbackManager mCallbackManager;

    //facebook token
    private String  mFacebookToken;
    //facebookId
    private String  mFacebookUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbdemo);

        //初始化FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        //初始化
        initView();
    }



    private void initView() {
        loginButton = (LoginButton) findViewById(R.id.lb_facebook);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFaceBook();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode,
                resultCode, data);
    }

    /**
     * 登录FaceBook
     */
    private void loginFaceBook(){
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        // Register a callback to respond to the user
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(FBDemoActivity.this,"facebook 登录成功",Toast.LENGTH_SHORT).show();
                AccessToken accessToken = loginResult.getAccessToken();
                if (accessToken != null) {
                    mFacebookToken = accessToken.getToken();
                    mFacebookUserId = accessToken.getUserId();
                    getFacebookUser();
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(FBDemoActivity.this, "Facebook授权被取消", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(FacebookException e) {
                e.printStackTrace();
                Log.i("test11",e.toString());
                Toast.makeText(FBDemoActivity.this, "Facebook授权失败", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }


    /**
     *  获取facebook 用户信息
     */
    private void getFacebookUser(){
        FBUserRequest.makeUserRequest(new GetFBUserCallback(new GetFBUserCallback.IGetUserResponse() {
            @Override
            public void onCompleted(FBUser user) {
                //保存用户图像
                Toast.makeText(FBDemoActivity.this,"facebook获取用户的名称为："+user.getName(),Toast.LENGTH_SHORT).show();
            }
        }).getCallback());

    }

}
