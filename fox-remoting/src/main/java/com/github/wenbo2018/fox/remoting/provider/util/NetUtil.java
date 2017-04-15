package com.github.wenbo2018.fox.remoting.provider.util;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shenwenbo on 2016/9/30.
 */
public class NetUtil {

    static Logger LOGGER=Logger.getLogger(NetUtil.class);
    public static String getHostIp() {
        try {
           return InetAddress.getLocalHost().getHostAddress().toString();
        } catch (UnknownHostException e) {
            LOGGER.error("get host ip erroe"+e);
        }
        return null;
    }

}
