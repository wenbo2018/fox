package com.github.wenbo2018.fox.admin.utils;


/**
 * Created by wenbo.shen on 2017/6/26.
 */
public class StringUtils {

    public static String arrayToString(String[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (i == (array.length-1)) {
                sb.append(array[i]);
            } else {
                sb.append(array[i]+",");
            }
        }
        return sb.toString();
    }

}
