package com.dxy.testjava;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.rmi.runtime.Log;

public class MyClass {

    public static String subNumString(String string) {
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(string);
        return m.replaceAll("").trim();
    }


    public static void main(String [] args){
        String url=subNumString("价值10元， 返回70%书券");
        System.out.print(url);

    }






}
