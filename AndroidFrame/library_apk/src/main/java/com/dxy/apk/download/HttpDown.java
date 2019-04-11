package com.dxy.apk.download;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dxy.apk.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Calendar;

import androidx.appcompat.app.AlertDialog;

/**
 * Created by duxueyang on 2019/4/10.
 * HttpURLConnection downlaod apk
 * 静态内部类防止内存溢出
 */
public class HttpDown implements DownLoadAPKListener {

    /**
     * Download status
     */
    private static final int DOWN_NO_SDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;

    private static final String DOWN_PROGRESS = "down_progress";
    private static final String DOWN_TEMPFILESIZE = "down_tempFileSize";
    private static final String DOWN_TARGETFILESIZE = "down_targetFileSize";
    private static final String DOWN_APKURL = "down_apkUrl";

    private static DownHandler handler;

    private ProgressBar pb_httpdown;
    private TextView tv_httpdown;

    private String targetFileSizes;


    public HttpDown() {
        handler = new DownHandler(HttpDown.this);
    }

    public void downLoadAPK(Context context, String apkNetUrl, String tagetFolder) {
        if (!SdcardUtils.isExit()) {
            handler.sendEmptyMessage(DOWN_NO_SDCARD);
        } else {
            showUpdateDialog(context);
            new Thread(new DownRunnable(apkNetUrl, tagetFolder)).start();
        }
    }

    /**
     * 显示正在下载dialog
     *
     * @param context
     */
    private void showUpdateDialog(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_httpdown_progress, null);
        pb_httpdown = v.findViewById(R.id.pb_httpdown);
        tv_httpdown = v.findViewById(R.id.tv_httpdown);

        new AlertDialog
                .Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
                .setView(v)
                .setCancelable(false)
                .create()
                .show();
    }

    @Override
    public void noSDCard() {

    }

    @Override
    public void updateDownProgress(int progress, String tempFileSize, String targetFileSize) {
        if (TextUtils.isEmpty(targetFileSizes)) {
            targetFileSizes = targetFileSize;
        }
        pb_httpdown.setProgress(progress);
        tv_httpdown.setText(tempFileSize + "/" + targetFileSize);
    }

    @Override
    public void downloadFinish(String targetUrl) {

    }

    private static class DownRunnable implements Runnable {

        private String apkNetUrl;
        private String tagetFolder;

        public DownRunnable(String apkNetUrl, String tagetFolder) {
            this.apkNetUrl = apkNetUrl;
            this.tagetFolder = tagetFolder;
        }

        @Override
        public void run() {
            try {
                String filepath = SdcardUtils.createTargetFolderUrl(tagetFolder);
                long now = Calendar.getInstance().getTimeInMillis();
                String apkName = now + ".apk";
                String tmpApkName = now + ".tmp";

                File ApkFile = new File(filepath + "/" + apkName);
                File tmpFile = new File(filepath + "/" + tmpApkName);
                FileOutputStream fos = new FileOutputStream(tmpFile);
                URL url = new URL(apkNetUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                DecimalFormat df = new DecimalFormat("0.00");
                String targetFileSize = "";
                String tmpFileSize;
                int progress;
                int count = 0;
                byte buf[] = new byte[1024];
                do {
                    //获取整体进度
                    int readNum = is.read(buf);
                    if (readNum <= 0) {
                        if (tmpFile.renameTo(ApkFile)) {
                            Message message = Message.obtain();
                            message.what = DOWN_OVER;
                            Bundle bundle = new Bundle();
                            bundle.putString(DOWN_APKURL, tmpFile.getAbsolutePath());
                            message.setData(bundle);
                            handler.sendMessage(message);
                        }
                        break;
                    } else {
                        count += readNum;
                        //发送下载进度
                        tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
                        progress = (int) (((float) count / length) * 100);
                        Message message = Message.obtain();
                        Bundle bundle = new Bundle();
                        if (TextUtils.isEmpty(targetFileSize)) {
                            targetFileSize = df.format((float) length / 1024 / 1024) + "MB";
                            bundle.putString(DOWN_TARGETFILESIZE, targetFileSize);
                        }
                        bundle.putInt(DOWN_PROGRESS, progress);
                        bundle.putString(DOWN_TEMPFILESIZE, tmpFileSize);
                        message.setData(bundle);
                        message.what = DOWN_UPDATE;
                        handler.sendMessage(message);
                    }
                    fos.write(buf, 0, readNum);
                } while (true);
                fos.close();
                is.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class DownHandler extends Handler {

        private WeakReference<HttpDown> downloadWeakReference;

        public DownHandler(HttpDown httpDown) {
            downloadWeakReference = new WeakReference<>(httpDown);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (downloadWeakReference.get() != null) {
                switch (msg.what) {
                    case DOWN_NO_SDCARD:
                        downloadWeakReference.get().noSDCard();
                        break;
                    case DOWN_UPDATE:
                        int progress = msg.getData().getInt(DOWN_PROGRESS);
                        String tempFileSize = msg.getData().getString(DOWN_TEMPFILESIZE);
                        String targetFileSize = msg.getData().getString(DOWN_TARGETFILESIZE);
                        downloadWeakReference.get().updateDownProgress(progress, tempFileSize, targetFileSize);
                        break;
                    case DOWN_OVER:
                        String targetUrl = msg.getData().getString(DOWN_APKURL);
                        downloadWeakReference.get().downloadFinish(targetUrl);
                        break;
                }
            }
        }


    }


}
