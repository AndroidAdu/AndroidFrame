package com.dxy.library_permission.installApk;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;


import com.dxy.library_permission.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Android 8.0+
 * 安装未知来源页面
 */
public class InstallUnknownResourceActivity extends AppCompatActivity {

    private int UNKNOWNREQUEST = 1;
    public static final String APKURL = "apkUrl";
    private String apkUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_unknown_resource);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        apkUrl = getIntent().getStringExtra(APKURL);
        showPromptDialog();
    }

    /**
     * 提示需要去系统设置弹窗
     */
    private void showPromptDialog() {
        new AlertDialog.Builder(InstallUnknownResourceActivity.this)
                .setTitle("提示")
                .setCancelable(false)
                .setMessage("安装应用需要打开未知来源权限，请去设置中开启权限！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        intentToSetting();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }

    /**
     * 去系统设置安装未知来源权限
     */
    @TargetApi(26)
    private void intentToSetting() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, UNKNOWNREQUEST);
    }

    /**
     *  返回安装未知来源权限结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UNKNOWNREQUEST) {
            if (resultCode == RESULT_OK) {
                //去安装应用程序
                if(!TextUtils.isEmpty(apkUrl)){
                    InstallUtils.installApk(InstallUnknownResourceActivity.this,apkUrl);
                }
                finish();

            } else {
                Toast.makeText(this,"授权失败！",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
