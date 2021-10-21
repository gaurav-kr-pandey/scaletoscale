package com.s2s.scaletoscale.utils;

public class BlogUtils {

    public static String getTags(String[] tag){
        String t = "";
        for(String s : tag){
            t=t+s+" ";
        }
        return t.trim();
    }
}
