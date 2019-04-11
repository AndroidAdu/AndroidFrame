package com.dxy.apk.download;

import android.os.Environment;

import java.io.File;

/**
 * Created by duxueyang on 2019/4/10.
 */
public class SdcardUtils {

    public static boolean isExit(){
        return  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String createTargetFolderUrl(String targetFolder){
        if(isExit()){
            String savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+targetFolder;
            File file = new File(savePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return savePath;
        }
        return "";
    }


}
