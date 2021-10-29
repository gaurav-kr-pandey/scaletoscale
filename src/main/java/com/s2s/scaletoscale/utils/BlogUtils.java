package com.s2s.scaletoscale.utils;

import java.util.Base64;

public class BlogUtils {

    public static String getTextified(String secret){
        StringBuilder tmp = new StringBuilder();
        final int OFFSET = 4;
        for (int i = 0; i < secret.length(); i++) {
            tmp.append((char)(secret.charAt(i) - OFFSET));
        }

        String reversed = new StringBuffer(tmp.toString()).reverse().toString();
        return new String(Base64.getDecoder().decode(reversed));
    }


}
