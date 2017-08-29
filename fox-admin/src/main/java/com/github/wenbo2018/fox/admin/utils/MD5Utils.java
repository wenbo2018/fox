package com.github.wenbo2018.fox.admin.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wenbo.shen on 2017/5/29.
 */
public class MD5Utils {

    private static Logger logger= LoggerFactory.getLogger(MD5Utils.class);

    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr= null;
        try {
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            logger.error("md5 encoding error:{}",e);
        }
        return newstr;
    }
}
