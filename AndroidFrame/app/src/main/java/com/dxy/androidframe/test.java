package com.dxy.androidframe;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by duxueyang on 2019/5/29.
 */
public class test {


    public static String subNumString(String string) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return m.replaceAll("").trim();
    }



}
