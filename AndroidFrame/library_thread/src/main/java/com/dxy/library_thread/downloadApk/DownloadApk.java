package com.dxy.library_thread.downloadApk;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by duxueyang on 2019/4/10.
 * 下载Apk 的内容
 */
public class DownloadApk {

    /**
     * Download status
     */
    private static final int DOWN_NO_SDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;


    public void downLoadAPK(Context context) {
        DownHandler handler = new DownHandler(this);
    }

    /**
     * 下载没有SD卡
     */
    private void noSDCard() {

    }

    /**
     * 正在下载中
     */
    private void updateDownProgress() {

    }

    /**
     * 下载完成
     */
    private void downloadFinish() {

    }


    private Runnable DownloadRunnable = new Runnable() {
        @Override
        public void run() {

        }
    };


    private static class DownHandler extends Handler {

        private WeakReference<DownloadApk> downloadWeakReference;

        public DownHandler(DownloadApk downloadApk) {
            downloadWeakReference = new WeakReference<DownloadApk>(downloadApk);
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
                        downloadWeakReference.get().updateDownProgress();
                        break;
                    case DOWN_OVER:
                        downloadWeakReference.get().downloadFinish();
                        break;
                }
            }


        }


    }


}
