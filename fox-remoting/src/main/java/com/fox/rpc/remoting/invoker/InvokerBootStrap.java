package com.fox.rpc.remoting.invoker;

import com.fox.rpc.SpiServiceLoader;
import com.fox.rpc.remoting.invoker.api.ClientFactory;

/**
 * Created by wenbo2018 on 2016/8/26.
 */
public class InvokerBootStrap {

    private static volatile boolean isStartup = false;

    public static boolean isStartup() {
        return isStartup;
    }

    public static void startup() {
        /**
         * 首次获取服务会初始化相关配置
         */
//        if (!isStartup) {
//            synchronized (InvokerBootStrap.class) {
//                if (!isStartup) {
//                    ClientFactory clientFactory= SpiServiceLoader.newExtension(ClientFactory.class);
//                    clientFactory.init();
//                }
//            }
//        }
    }

}
