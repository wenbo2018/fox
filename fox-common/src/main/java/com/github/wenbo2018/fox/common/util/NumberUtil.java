package com.github.wenbo2018.fox.common.util;

/**
 * Created by wenbo.shen on 2017/5/19.
 */
public class NumberUtil {


    public static int parseInt(String str){
        return stringParseInt(str,0);
    }

    public static double parseDouble(String str) {
        return stringParseDouble(str,0);
    }


    public static double stringParseDouble(String str,int defaultValue){
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static int stringParseInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
